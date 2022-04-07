package com.example.neu_simplebackgroundtask.my_interface;

import com.example.neu_simplebackgroundtask.model.User;

// File interface nay se dinh nghia cac ham xu ly logic muon call back ra ben ngoai
public interface IClickItemUserListener {
    void getDetailUser (User user);
    void updateUser (int id, User user);
    void deleteUser (int id);
}
