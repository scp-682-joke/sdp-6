package tests;

import elements.DirectoryNode;
import elements.FileNode;
import visitors.SearchVisitor;
import visitors.StatisticsVisitor;
import visitors.TotalSizeVisitor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VisitorPatternTest {

    private DirectoryNode root;
    private DirectoryNode docs;
    private FileNode f1;
    private FileNode f2;

    @BeforeEach
    void setUp() {
        root = new DirectoryNode("root");
        docs = new DirectoryNode("docs");
        f1 = new FileNode("report.pdf", 2000);
        f2 = new FileNode("data.csv", 500);

        docs.add(f1);
        root.add(docs);
        root.add(f2);
    }

    @Test
    @DisplayName("T1: Double Dispatch Correctness")
    void testT1_DoubleDispatch() {
        TotalSizeVisitor visitor = new TotalSizeVisitor();
        f1.accept(visitor);
        assertEquals(2000, visitor.getTotalSize());
    }

    @Test
    @DisplayName("T2: Size Accumulation Across Multiple Levels")
    void testT2_SizeAccumulation() {
        TotalSizeVisitor visitor = new TotalSizeVisitor();
        root.accept(visitor);
        assertEquals(2500, visitor.getTotalSize());
    }

    @Test
    @DisplayName("T3: Search Combines Name and Size Filter")
    void testT3_SearchCombinedFilter() {
        SearchVisitor v1 = new SearchVisitor("report", 1000);
        root.accept(v1);
        assertEquals(1, v1.getMatchingPaths().size());

        SearchVisitor v2 = new SearchVisitor("report", 3000);
        root.accept(v2);
        assertTrue(v2.getMatchingPaths().isEmpty());
    }

    @Test
    @DisplayName("T4: Search Produces Deterministic Root-Relative Paths")
    void testT4_DeterministicPaths() {
        SearchVisitor visitor = new SearchVisitor("", 0);
        root.accept(visitor);
        List<String> paths = visitor.getMatchingPaths();

        assertEquals(2, paths.size());
        assertEquals("root/data.csv", paths.get(0));
        assertEquals("root/docs/report.pdf", paths.get(1));
    }

    @Test
    @DisplayName("T5: Statistics Correctness")
    void testT5_Statistics() {
        StatisticsVisitor stats = new StatisticsVisitor();
        root.accept(stats);

        assertEquals(2, stats.getFileCount());
        assertEquals(2, stats.getDirectoryCount()); // root + docs
        assertEquals(1, stats.getMaxDepth());
    }

    @Test
    @DisplayName("T6: Empty Structures and Edge Cases")
    void testT6_EmptyStructures() {
        DirectoryNode emptyDir = new DirectoryNode("empty");
        TotalSizeVisitor sizeVisitor = new TotalSizeVisitor();
        emptyDir.accept(sizeVisitor);
        assertEquals(0, sizeVisitor.getTotalSize());

        StatisticsVisitor statsVisitor = new StatisticsVisitor();
        emptyDir.accept(statsVisitor);
        assertEquals(0, statsVisitor.getFileCount());
        assertEquals(1, statsVisitor.getDirectoryCount());
        assertEquals(0, statsVisitor.getMaxDepth());
    }

    @Test
    @DisplayName("T7: State Isolation Across Executions")
    void testT7_StateIsolation() {
        TotalSizeVisitor visitor = new TotalSizeVisitor();
        root.accept(visitor);
        assertEquals(2500, visitor.getTotalSize());

        TotalSizeVisitor newVisitor = new TotalSizeVisitor();
        docs.accept(newVisitor);
        assertEquals(2000, newVisitor.getTotalSize());
    }

    @Test
    @DisplayName("T8: Tree Integrity Invariants")
    void testT8_TreeIntegrity() {
        assertThrows(IllegalArgumentException.class, () -> new FileNode("bad.txt", -100));
        assertThrows(IllegalArgumentException.class, () -> root.add(new FileNode("data.csv", 100)));
        assertThrows(IllegalArgumentException.class, () -> root.add(root));
    }
}
