package KanbanBoard;

import java.util.ArrayList;
import java.util.List;

public class Node<String>{
    private List<Node<String>> children = new ArrayList<>();

    private Node<String> parent = null;

    public Node<String> addChild(Node<String> child){
        child.setParent(this);
        this.children.add(child);
        return child;
    }

    public void addChildren(List<Node<String>> children){
        children.forEach(each -> each.setParent(this));
        this.children.addAll(children);
    }

    public List<Node<String>> getChildren(){
        return children;
    }

    public Node<String> getParent() {
        return parent;
    }

    public void setParent(Node<String> parent) {
        this.parent = parent;
    }
}
