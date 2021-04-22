package com.lhy.seckill.core.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Description 分页类
 * @Author lihengyu
 * @Date 2021/4/17 17:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyPage<T> {

    int current = 1;
    int size = 10;
    int total = 0;
    int pages = (int)Math.ceil((double)this.total/(double)this.size) ;
    List<T> records;

    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;

    public void judgePageBoundary() {
        this.isFirstPage = this.current == 1;
        this.isLastPage = this.current == pages || pages == 1;
        this.hasPreviousPage = this.current > 1;
        this.hasNextPage = this.current < pages;
    }

    public MyPage(int current , int size , int total , List<T> records){
        this.current = current;
        this.size=size;
        this.total=total;
        this.records=records;
        this.pages=(int)Math.ceil((double)total/(double)size);
    }
}
