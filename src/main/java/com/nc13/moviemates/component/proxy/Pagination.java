package com.nc13.moviemates.component.proxy;

import org.springframework.stereotype.Component;

@Component
public class Pagination {
    private int totalCount, startRow, endRow,
            pageCount, pageSize, startPage, endPage, pageNum,
            blockCount, prevBlock, nextBlock, blockNum;

    private String tname;
    private boolean existPrev, existNext;
    private final int PAGE_SIZE = 10;
    public final int BLOCK_SIZE = 5;
    // 데베: 카운트
    public Pagination(int pageNum, int count, int pageSize) {
        this.pageSize = pageSize; // 1페이지당 들어갈 로우의 개수
        this.pageNum = pageNum; // 밑의 숫자
        this.totalCount = count; // 총 로우의 수
        this.pageCount = (totalCount % pageSize !=0) ? totalCount % pageSize +1 : totalCount % pageSize;
        this.blockCount = (pageCount % BLOCK_SIZE != 0) ? pageCount % BLOCK_SIZE +1 : pageCount % BLOCK_SIZE;
        this.startRow = (pageNum-1)*pageSize;
        this.endRow = (pageCount != pageNum) ? startRow + pageSize-1: totalCount-1;
        this.blockNum = (pageNum -1) / BLOCK_SIZE;
        this.startPage = blockNum* BLOCK_SIZE-1;
        this.endPage = startPage + BLOCK_SIZE -1;
        this.existPrev = blockNum - 1 !=0;
        this.existNext = blockNum + 1 != blockCount;
        this.nextBlock = startPage + BLOCK_SIZE;
        this.prevBlock = startPage - BLOCK_SIZE;
    }
}
