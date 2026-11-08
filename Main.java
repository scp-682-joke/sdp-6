import elements.DirectoryNode;
import elements.FileNode;
import visitors.SearchVisitor;
import visitors.StatisticsVisitor;
import visitors.TotalSizeVisitor;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Visitor Pattern FileSystem Demo ===");

        // file tree
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode src = new DirectoryNode("src");
        DirectoryNode docs = new DirectoryNode("docs");

        FileNode mainJava = new FileNode("Main.java", 1500);
        FileNode readme = new FileNode("README.md", 2500);
        FileNode logo = new FileNode("logo.png", 50000);

        src.add(mainJava);
        docs.add(readme);
        docs.add(logo);

        root.add(src);
        root.add(docs);

        // 1. TotalSizeVisitor
        TotalSizeVisitor sizeVisitor = new TotalSizeVisitor();
        root.accept(sizeVisitor);
        System.out.println("Total System Size: " + sizeVisitor.getTotalSize() + " bytes");

        // 2. SearchVisitor
        SearchVisitor searchVisitor = new SearchVisitor(".md", 1000);
        root.accept(searchVisitor);
        System.out.println("Search Results (.md >= 1000 bytes): " + searchVisitor.getMatchingPaths());

        // 3. StatisticsVisitor
        StatisticsVisitor statsVisitor = new StatisticsVisitor();
        root.accept(statsVisitor);
        System.out.println("Statistics -> Files: " + statsVisitor.getFileCount()
                + ", Directories: " + statsVisitor.getDirectoryCount()
                + ", Max Depth: " + statsVisitor.getMaxDepth());
    }
}
