package elements;

import visitors.Visitor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DirectoryNode implements Element {
    private final String name;
    private final List<Element> children = new ArrayList<>();

    public DirectoryNode(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Directory name cannot be empty");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Element> getChildren() {
        return Collections.unmodifiableList(children);
    }

    public void add(Element element) {
        if (element == null) {
            throw new IllegalArgumentException("Cannot add null element");
        }
        if (element == this) {
            throw new IllegalArgumentException("Cannot add directory to itself (cycle detected)");
        }

        String elementName = getElementName(element);
        for (Element child : children) {
            if (getElementName(child).equals(elementName)) {
                throw new IllegalArgumentException("Duplicate sibling name not allowed: " + elementName);
            }
        }
        children.add(element);
    }

    public void remove(Element element) {
        children.remove(element);
    }

    private String getElementName(Element element) {
        if (element instanceof FileNode f) return f.getName();
        if (element instanceof DirectoryNode d) return d.getName();
        return "";
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
