package br.edu.ifpb.agora.model;

public class NavPageBuilder {

    private NavPage paginator;

    public static NavPage newNavPage(int currentPage, long totalItems, int totalPages, int pageSize){
        NavPageBuilder builder = new NavPageBuilder();
        builder.start();
        builder.setCurrentPage(currentPage);
        builder.setTotalItems(totalItems);
        builder.setTotalPages(totalPages);
        builder.setPageSize(pageSize);
        return builder.finish();
    }

    private NavPage finish() {
        return this.paginator;
    }

    private void setPageSize(int pageSize) {
        this.paginator.setPageSize(pageSize);
    }

    
    private void setTotalPages(int totalPages) {
        this.paginator.setTotalPages(totalPages);
    }

    private void setTotalItems(long totalItems) {
        this.paginator.setTotalItems(totalItems);
    }

    private void setCurrentPage(int currentPage) {
        this.paginator.setCurrentPage(currentPage);
    }

    private NavPageBuilder start() {
        this.paginator = new NavPage();
        return this;
    }
    
}
