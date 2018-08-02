package com.ssm.model.bean;



    //点数据
public  class Point{
        private int cid;
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private float[] vec;
        private boolean visited;
        private boolean center;

        public int getCid() {
            return cid;
        }

        public void setCid(int cid) {
            this.cid = cid;
        }

        public float[] getVec() {
            return vec;
        }

        public void setVec(float[] vec) {
            this.vec = vec;
        }

        public boolean isVisited() {
            return visited;
        }

        public void setVisited(boolean visited) {
            this.visited = visited;
        }

        public boolean isCenter() {
            return center;
        }

        public void setCenter(boolean center) {
            this.center = center;
        }
    }

