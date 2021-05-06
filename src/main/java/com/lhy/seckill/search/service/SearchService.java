package com.lhy.seckill.search.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import com.lhy.seckill.core.base.MyPage;
import com.lhy.seckill.goods.entity.ElasticSearchGoodsEntity;
import com.lhy.seckill.goods.entity.SkGoodsEntity;
import com.lhy.seckill.goods.mapper.GoodsMapper;
import com.lhy.seckill.goods.mapper.GoodsRepository;
import com.lhy.seckill.search.controller.param.GoodsSearchParam;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description search
 * @Author lihengyu
 * @Date 2021/4/25 18:23
 */
@Service
public class SearchService {
    @Autowired
    ElasticsearchRestTemplate template;
    @Autowired
    private GoodsRepository repository;
    @Autowired
    private GoodsMapper goodsMapper;


    /** @Description 构建es分页 分词 高亮查询
     * @Param [param]
     * @return com.lhy.seckill.core.base.MyPage<com.lhy.seckill.goods.entity.SkGoodsEntity>
     **/
    public MyPage<SkGoodsEntity> search(GoodsSearchParam param){
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("name", param.getName()))
                .should(QueryBuilders.matchQuery("description", param.getDescription()));

        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withPageable(PageRequest.of(param.getCurrent(),param.getSize()))
                .withQuery(queryBuilder)
                .withHighlightFields(
                        new HighlightBuilder.Field("name"),
                        new HighlightBuilder.Field("description"))
                .withHighlightBuilder(new HighlightBuilder().preTags("->").postTags("<-"))
                .build();

        SearchHits<ElasticSearchGoodsEntity> search = template.search(searchQuery, ElasticSearchGoodsEntity.class);
        List<SearchHit<ElasticSearchGoodsEntity>> searchHits = search.getSearchHits();
        ArrayList<ElasticSearchGoodsEntity> goodsEntities = Lists.newArrayListWithCapacity(searchHits.size());
        for (SearchHit<ElasticSearchGoodsEntity> searchHit : searchHits) {
            Map<String, List<String>> highlightFields = searchHit.getHighlightFields();

            searchHit.getContent().setName(highlightFields.get("name") == null ?  searchHit.getContent().getName() : highlightFields.get("name").get(0));
            searchHit.getContent().setDescription(highlightFields.get("description") == null ?  searchHit.getContent().getDescription() : highlightFields.get("description").get(0));

            goodsEntities.add(searchHit.getContent());
        }

        //获取es中的数据后根据id去mysql查询详情
        List<SkGoodsEntity> skGoodsEntities = Lists.newArrayListWithCapacity(goodsEntities.size());
        for (ElasticSearchGoodsEntity goodsEntity : goodsEntities) {
            QueryWrapper<SkGoodsEntity> wrapper = new QueryWrapper<>();
            wrapper.eq("id",goodsEntity.getId());
            SkGoodsEntity skGoodsEntity = goodsMapper.selectOne(wrapper);
            skGoodsEntity.setName(goodsEntity.getName());
            skGoodsEntity.setDescription(goodsEntity.getDescription());
            skGoodsEntities.add(skGoodsEntity);
        }
        return new MyPage<SkGoodsEntity>(param.getCurrent(),param.getSize(),searchHits.size(),skGoodsEntities);
    }
}
