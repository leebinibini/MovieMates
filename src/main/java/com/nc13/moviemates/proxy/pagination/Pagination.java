
package com.nc13.moviemates.proxy.pagination;
import static java.util.stream.Collectors.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component("page")
@Data
@Lazy
public class Pagination {
    private int totalCount, startRow, endRow,
            pageCount, pageSize,  startPage, endPage, pageNum,
            blockCount, prevBlock, nextBlock, blockNum;
    public final int BLOCK_SIZE = 5;
    private String tname;
    private boolean existPrev, existNext;
    public Pagination(){}

    public Pagination(String tname, int pageSize, int pageNum, int count) {
        this.tname = tname;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.totalCount = count;
        this.pageCount = count % pageSize != 0 ? count / pageSize + 1 : count / pageSize;
        this.blockCount = pageCount % BLOCK_SIZE != 0 ? pageCount / BLOCK_SIZE + 1 : pageCount / BLOCK_SIZE;
        this.startRow = (pageNum - 1) * pageSize;
        this.endRow = pageNum != pageCount ? startRow + pageSize - 1 : totalCount - 1;
        this.blockNum = (pageNum - 1) / BLOCK_SIZE ;
        this.startPage = blockNum * BLOCK_SIZE + 1;
        this.endPage = blockNum != blockCount - 1 ? startPage + BLOCK_SIZE - 1 : pageCount;
        this.existPrev = blockNum != 0;
        this.existNext = (blockNum + 1) != blockCount ;
        this.nextBlock = startPage + BLOCK_SIZE;
        this.prevBlock = startPage - BLOCK_SIZE;
    }
}