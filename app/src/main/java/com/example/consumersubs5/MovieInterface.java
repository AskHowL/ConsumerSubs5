package com.example.consumersubs5;


import com.example.consumersubs5.Model.Movie;

import java.util.ArrayList;

public interface MovieInterface {
    void preExecute();
    void postExecute(ArrayList<Movie> movies);
}
