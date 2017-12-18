//
//  WorkoutEx.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 12/9/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import Foundation

class WorkoutEx {
    
    var name = "Drop here"
    var img = ""
    var tube1 = "N"
    var tube2 = "N"
    var tube3 = "N"
    var reps = 0
    var actualReps = 0
    var restTime = 5
    var exReps = 1
    var exTime = 0
    var actualExTime = 0
    var repsTimeBool = false
    
    
    init(name: String, img: String, tube1: String, tube2: String, tube3: String, reps: Int, restTime: Int, exReps: Int, exTime: Int, repsTimeBool: Bool) {
    
        self.name = name
        self.img = img
        self.tube1 = tube1
        self.tube2 = tube2
        self.tube3 = tube3
        self.reps = reps
        self.restTime = restTime
        self.exReps = exReps
        self.exTime = exTime
        self.repsTimeBool = repsTimeBool
    }
    
    init(name: String, img: String, tube1: String, tube2: String, tube3: String, reps: Int, actualReps: Int, restTime: Int, exReps: Int,
         exTime: Int, actualExTime: Int, repsTimeBool: Bool) {
    
        self.name = name;
        self.img = img;
        self.tube1 = tube1;
        self.tube2 = tube2;
        self.tube3 = tube3;
        self.reps = reps;
        self.actualReps = actualReps;
        self.restTime = restTime;
        self.exReps = exReps;
        self.exTime = exTime;
        self.actualExTime = actualExTime;
        self.repsTimeBool = repsTimeBool;
        
    }
    
    init() {
        
    }
    
    
}
