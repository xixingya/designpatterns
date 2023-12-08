package tech.xixing.elasticsearch.struct;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;

/**
 * @author liuzhifei
 * @since 1.0
 */
public class KdTree {
    private static final int K = 2; // 二维空间

    private Node root;

    private class Node {
        private double[] point;
        private Node left, right;

        public Node(double[] point) {
            this.point = point;
        }
    }

    public void insert(double[] point) {
        if (point == null || point.length != K) {
            throw new IllegalArgumentException("Invalid point");
        }

        root = insert(root, point, 0);
    }

    private Node insert(Node node, double[] point, int depth) {
        if (node == null) {
            return new Node(point);
        }

        if (point[depth % K] < node.point[depth % K]) {
            node.left = insert(node.left, point, depth + 1);
        } else {
            node.right = insert(node.right, point, depth + 1);
        }

        return node;
    }

    public boolean contains(double[] point) {
        if (point == null || point.length != K) {
            throw new IllegalArgumentException("Invalid point");
        }

        return contains(root, point, 0);
    }

    private boolean contains(Node node, double[] point, int depth) {
        if (node == null) {
            return false;
        }

        if (Arrays.equals(node.point, point)) {
            return true;
        }

        if (point[depth % K] < node.point[depth % K]) {
            return contains(node.left, point, depth + 1);
        } else {
            return contains(node.right, point, depth + 1);
        }
    }

    public void rangeSearch(double[] min, double[] max, List<double[]> results) {
        if (min == null || min.length != K || max == null || max.length != K) {
            throw new IllegalArgumentException("Invalid range");
        }

        rangeSearch(root, min, max, 0, results);
    }

    private void rangeSearch(Node node, double[] min, double[] max, int depth, List<double[]> results) {
        if (node == null) {
            return;
        }

        if (inRange(node.point, min, max)) {
            results.add(node.point);
        }

        if (min[depth % K] < node.point[depth % K]) {
            rangeSearch(node.left, min, max, depth + 1, results);
        }

        if (max[depth % K] >= node.point[depth % K]) {
            rangeSearch(node.right, min, max, depth + 1, results);
        }
    }

    public List<double[]> nearestNeighbor(double[] point, int k) {
        if (point == null || point.length != K || k <= 0) {
            throw new IllegalArgumentException("Invalid input");
        }


        PriorityQueue<double[]> neighbors = new PriorityQueue<>(
                (a, b) -> Double.compare(distance(a, point), distance(b, point))
        );

        nearestNeighbor(root, point, k, neighbors, 0);
        List<double[]> list = new ArrayList<>();
        while (!neighbors.isEmpty() && list.size() < k) {
            list.add(neighbors.poll());
        }
        return list;
    }

    private void nearestNeighbor(Node node, double[] point, int k, PriorityQueue<double[]> neighbors, int depth) {
        if (node == null) {
            return;
        }

        double dist = distance(node.point, point);
        if (neighbors.size() < k || dist < distance(neighbors.peek(), point)) {
            neighbors.offer(node.point);
            if (neighbors.size() > k) {
                neighbors.poll();
            }
        }

        double axisDist = point[depth % K] - node.point[depth % K];
        Node first = axisDist < 0 ? node.left : node.right;
        Node second = axisDist < 0 ? node.right : node.left;

        nearestNeighbor(first, point, k, neighbors, depth + 1);

        if (axisDist * axisDist < distance(neighbors.peek(), point)) {
            nearestNeighbor(second, point, k, neighbors, depth + 1);
        }
    }

    private double distance(double[] a, double[] b) {
        double sum = 0.0;
        for (int i = 0; i < K; i++) {
            sum += (a[i] - b[i]) * (a[i] - b[i]);
        }
        return Math.sqrt(sum);
    }

    private boolean inRange(double[] point, double[] min, double[] max) {
        for (int i = 0; i < K; i++) {
            if (point[i] < min[i] || point[i] > max[i]) {
                return false;
            }
        }
        return true;
    }

}
