package org.java.tree;

/**
 * @author huangming
 * @date 2020/9/20 22:18
 * 红黑树(RedBlackTree) 特性:
 * 1.根节点一定是黑色的
 * 2.节点颜色不是黑色就是红色
 * 3.新增的节点默认为红色
 * 4.不允许有两个相连续的红色节点
 * 5.红色节点下的两个子节点一定是黑色
 */
public class RedBlackTree {


    /**
     * 红黑树节点
     */
    class Node {

        //节点值
        private int value;

        //父节点
        private Node parent;

        //左节点
        private Node left;

        //右节点
        private Node right;

        //节点颜色
        private NodeColor nodeColor;


        public Node(int value, Node parent, Node left, Node right, NodeColor nodeColor) {
            this.value = value;
            this.parent = parent;
            this.left = left;
            this.right = right;
            this.nodeColor = nodeColor;
        }

        public Node() {

        }

    }

    //红黑树根节点
    private Node root;

    /**
     * 新增节点
     *
     * @param value 节点值
     */
    public void insert(int value) {
        root = insert(root, value);
    }

    /**
     * 新增节点
     *
     * @param node  当前节点
     * @param value 节点值
     * @return
     */
    public Node insert(Node node, int value) {
        //如果根节点为则创建根节点
        if (root == null) {
            //根节点默认为黑色
            return new Node(value, null, null, null, NodeColor.Black);
        } else {
            //不是根节点,表示之前已存在节点
            Node newNode = addNode(node, value);
            //修复节点
            repairTree(newNode);

        }
        return root;
    }

    public Node addNode(Node node, int value) {
        if (value > node.value) {
            //右子树，如果节点为空就新增右节点
            if (node.right == null) {
                //新增默认父节点为node, 节点颜色为红色
                Node newNode = new Node(value, node, null, null, NodeColor.red);
                node.right = newNode;
                return newNode;
            } else {
                //如果不为空递归循环新增
                return addNode(node.right, value);
            }
        } else {
            //左子树
            if (node.left == null) {
                //新增默认父节点为node, 节点颜色为红色
                Node newNode = new Node(value, node, null, null, NodeColor.red);
                node.left = newNode;
                return newNode;
            } else {
                //如果不为空递归循环新增
                return addNode(node.left, value);
            }
        }
    }


    /**
     * 修复红黑树
     *
     * @param newNode 新增的节点
     */
    public void repairTree(Node newNode) {
        //爷爷节点
        Node grandfatherNode = grandfatherNode(newNode);
        //父节点
        Node parentNode = getParentNode(newNode);
        //如果当前新增节点的父节点也为红色,则需要进行修复或者旋转 (新增节点默认为红色)
        if (parentNode != null && parentNode.nodeColor == NodeColor.red) {
            //判断当前节点为左子树还是右子树
            boolean flag = parentNode.right == newNode ? true : false;

            //取叔叔节点  (如果爷爷节点左边为父节点,则叔叔节点在右边,反之在左边)
            Node uncleNode = grandfatherNode.left == parentNode ? grandfatherNode.right : grandfatherNode.left;
            //如果叔叔节点为红色则表示需要变色
            if (uncleNode != null && uncleNode.nodeColor == NodeColor.red) {
                /**1.将父节点变为黑色
                 * 2.将叔叔节点也变为黑色
                 * 3.将祖父节点变为红色
                 * 4.将指针指向祖父节点进行
                 */
                parentNode.nodeColor = NodeColor.Black;
                uncleNode.nodeColor = NodeColor.Black;
                grandfatherNode.nodeColor = NodeColor.red;
                repairTree(grandfatherNode);
            } else {
                // 叔叔节点为黑色 ,则需要进行旋转
                //flag为true表示当前节点为右子树,反之为左子树
                if (flag) {
                    //左旋转
                    //父亲节点为红色,叔叔节点为黑色,并且当前节点为右子树 以爷爷节点进行左旋
                    leftRotate(grandfatherNode);
                } else {
                    //右旋转
                    // 父亲节点为红色,叔叔节点为黑色,并且当前节点为左子树  以爷爷节点进行右旋转
                    rightRotate(grandfatherNode);
                }
                //旋转变色 父节点变为黑色, 爷爷节点变为红色
                parentNode.nodeColor = NodeColor.Black;
                grandfatherNode.nodeColor = NodeColor.red;
                //以祖父节点再次检测是否需要修复
                repairTree(grandfatherNode);
            }
        }
        root.nodeColor = NodeColor.Black; //设置根节点为黑色
    }

    /**
     * 左旋转
     *
     * @param node 以该节点进行旋转
     */
    public void leftRotate(Node node) {
        //取得当前节点的右子节点
        Node rightNode = node.right;
        //设置当前节点的右节点为 右子节点的左子节点
        node.right = rightNode.left;

        //如果 rightNode的左子节点不为空,修改左子树引用
        if (rightNode.left != null) {
            rightNode.left.parent = node;
        }
        //如果node节点为根节点,则右子树直接上位为根节点
        if (node.parent == null) {
            root = rightNode;
        } else {
            // node 不是root节点
            // 判断node节点是否为左子节点, 设置node.parent新子节点引用
            if (node.parent.left == node) {
                node.parent.left = rightNode;
            } else {
                node.parent.right = rightNode;
            }
        }
        //设置rightNode左子节点为 node
        rightNode.left = node;
        //修改rightNode.parent节点引用
        rightNode.parent = node.parent;
        node.parent = rightNode;
    }


    /**
     * 右旋转
     *
     * @param node 以该节点进行旋转
     */
    public void rightRotate(Node node) {
        //取得当前节点的左子节点
        Node leftNode = node.left;
        //设置当前节点的左子节点为leftNode的右子节点
        node.left = leftNode.right;

        //如果leftNode.right 不为空则设置父节点引用
        if (leftNode.right != null) {
            leftNode.right.parent = node;
        }
        //如果当前节点是根节点, leftNode直接上位根节点
        if (node.parent == null) {
            root = leftNode;
        } else {
            //设置当前节点父节点子节点的引用
            if (node.parent.left == node) node.parent.left = leftNode;
            if (node.parent.right == node) node.parent.right = leftNode;
        }
        //设置leftNode的right节点
        leftNode.right = node;
        //设置父节点引用
        leftNode.parent = node.parent;
        node.parent = leftNode;
    }


    /**
     * 取得父节点
     * @param node
     * @return
     */
    public Node getParentNode(Node node) {
        if (node != null) {
            return node.parent;
        }
        return null;
    }

    /**
     * 取爷爷节点
     * @param node
     * @return
     */
    public Node grandfatherNode(Node node) {
        if (node != null) {
            return getParentNode(node) != null ? getParentNode(node).parent : null;
        }
        return null;
    }


    public static void main(String[] args) {
        RedBlackTree redBlackTree = new RedBlackTree();
        redBlackTree.insert(6);
        redBlackTree.insert(7);
        redBlackTree.insert(8);
        redBlackTree.insert(5);
        redBlackTree.insert(4);
        redBlackTree.insert(3);
        redBlackTree.insert(2);
        redBlackTree.insert(9);
        redBlackTree.insert(10);
        redBlackTree.insert(15);
        redBlackTree.insert(18);
        redBlackTree.print_tree();
    }


    public void padding(String ch, int n) {
        int i;
        for (i = 0; i < n; i++)
            System.out.printf(ch);
    }

    void print_node(Node root, int level) {
        if (root == null) {
            padding("\t", level);
            System.out.println("NIL");
        } else {
            print_node(root.right, level + 1);
            padding("\t", level);
            if (root.nodeColor == NodeColor.Black) {
                System.out.printf("(%d)\n", root.value);
            } else
                System.out.printf("%d\n", root.value);
            print_node(root.left, level + 1);

        }

    }

    void print_tree() {
        print_node(this.root, 0);
        System.out.printf("-------------------------------------------\n");
    }


}
