package org.java.tree;

import java.util.LinkedList;
import java.util.Queue;

/**
 * @author huangming
 * @date 2020/9/7 13:42
 * <p>
 * 二叉搜索树（BST） 特性:
 * 第一个添加得节点会做为平衡值,也就是根节点
 * 左子节点必须小于根节点,右子节点必须大于根节点
 * 所有根节点得子节点也必须都为 二叉搜索树
 */
public class BinarySearchTree {

    public static class Node {
        //当前节点值
        int data;
        //左子节点
        Node left;
        //右子节点
        Node right;

        public Node() {
        }

        public Node(int data, Node left, Node right) {
            this.data = data;
            this.left = left;
            this.right = right;
        }
    }

    //跟节点
    private Node root;
    //二叉搜索树节点个数
    private int size;

    //默认根节点为null,节点个数为0
    public BinarySearchTree() {
        this.root = null;
        this.size = 0;
    }


    public boolean lookup(int data) {
        return lookup(root,data);
    }

    /**
     * 查找节点值是否存在树中
     *
     * @param node 当前节点
     * @param data 查找的值
     * @return 返回的结果
     */
    public boolean lookup(Node node, int data) {
        //如果节点为空则直接返回false
        if (node == null) {
            return false;
        } else {
            //如果查找的值和当前节点的值相等 返回true
            if (data == node.data) {
                return true;
            } else {
                //不相等,递归查找
                return (data > node.data) ? lookup(node.right, data) : lookup(node.left, data);
            }
        }
    }

    public void insert(int data) {
        root = insert(root, data);
    }

    /**
     * 新增节点
     *
     * @param node 当前根节点
     * @param data 新增的值
     * @return
     */
    public Node insert(Node node, int data) {
        //如果节点为空,则创建一个新节点并返回
        if (node == null) {
            size++;
            return new Node(data, null, null);
        } else {
            //节点不为空,则根据data与节点的data比较
            if (data > node.data)
                //通过递归设置节点的右节点
                node.right = insert(node.right, data);
            else {
                node.left = insert(node.left, data);
            }
            return node;
        }
    }

    /**
     * 得到节点个数
     *
     * @return 节点个数
     */
    public int getSize() {
        return size;
    }

    /**
     * 先序遍历
     * 特性:根节点->左子节点->右子节点
     */
    public void preOrder() {
        preOrder(root);
    }

    public void preOrder(Node node) {
        if (node == null) {
            return;
        }
        System.out.println(node.data);
        preOrder(node.left);
        preOrder(node.right);
    }

    /**
     * 中序遍历
     * 左子节点->根节点->右子节点
     */
    public void inOrder() {
        inOrder(root);
    }

    public void inOrder(Node node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        System.out.println(node.data);
        inOrder(node.right);

    }

    /**
     * 后序遍历
     * 左子节点->右子节点->根节点
     */
    public void postOrder() {
        postOrder(root);
    }

    public void postOrder(Node node) {
        if (node == null) {
            return;
        }
        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.data);
    }

    /**
     * 层次遍历
     * 特性：从上到下,从左到右
     * 采用队列来实现
     * 队列:先进先处 栈:先进后出
     */
    public void leveOrder() {
        leveOrder(root);
    }

    public void leveOrder(Node node) {
        Queue<Node> queue = new LinkedList<>(); //创建一个队列
        queue.add(node); //将根元素新增值队列中
        //如果队列中有值则一直循环
        while (!queue.isEmpty()) {
            //通过remove方法得到当前节点
            Node curr = queue.remove();
            System.out.println(curr.data);
            //如果当前节点的左,右节点不为空则新增进来输出该值
            if (curr.left != null) {
                queue.add(curr.left);
            }
            if (curr.right != null) {
                queue.add(curr.right);
            }
        }
    }

    public static void main(String[] args) {
        BinarySearchTree binarySearchTree = new BinarySearchTree();
        binarySearchTree.insert(5);
        binarySearchTree.insert(6);
        binarySearchTree.insert(4);
        binarySearchTree.insert(2);
        binarySearchTree.insert(3);
        binarySearchTree.insert(8);
        binarySearchTree.insert(10);
        System.out.println(binarySearchTree.lookup(7));
    }




}

