package com.example.lucas.lucasvanberkel_pset4;


public class Todo {
    String todo;
    int status;

    public Todo(String todo, int status) {
        this.todo = todo;
        this.status = status;
    }

    public void setStatus(int status){
        this.status = status;
    }

    public int getStatus(){
        return this.status;
    }
}
