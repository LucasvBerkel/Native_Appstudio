package com.example.lucas.lucasvanberkel_pset5;


class Todo {
    String todo;
    int status;

    Todo(String todo, int status) {
        this.todo = todo;
        this.status = status;
    }

    void setStatus(int status){
        this.status = status;
    }

    int getStatus(){
        return this.status;
    }
}
