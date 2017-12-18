//
//  Workout.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 12/11/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import Foundation
import SwiftyJSON

class Workout {
    
    var workoutID = ""
    var workoutName = ""
    var exercisesJSON = ""
    var exercisesList = Array<WorkoutEx>()
    var workoutReps = 0
    var workoutType = ""
    var wTime = 0
    
    
    init(workoutName: String, exercisesList: Array<WorkoutEx> , workoutReps: Int){
    
        self.workoutName = workoutName
        self.exercisesList = exercisesList
        self.workoutReps = workoutReps
        
    }
    
    init(){
        
    }
    
    init(workoutID: String, workoutName: String, exercisesJSON: String){
    
        self.workoutID = workoutID
        self.workoutName = workoutName
        self.exercisesJSON = exercisesJSON
        
        JSONtoArray()
    }
    
    init (workoutID: String, workoutName: String, workoutReps: Int, exercisesJSON: String, workoutType: String, wTime: Int){
    
        self.workoutID = workoutID
        self.workoutName = workoutName
        self.workoutReps = workoutReps
        self.exercisesJSON = exercisesJSON
        self.workoutType = workoutType
        self.wTime = wTime
        
        JSONtoArray()
    
    }

    func JSONtoArray(){
    
        exercisesList = Array<WorkoutEx>()
    
        if let dataFromString = exercisesJSON.data(using: .utf8, allowLossyConversion: false) {
           
            do{
             
                let json = try JSON(data: dataFromString)
                
                
                for i in (0..<json.count){
                    
                    let exObj = json[i]
                    let name = exObj["name"].string!
                    let img = exObj["image"].string!
                    let tube1 = exObj["tube1"].string!
                    let tube2 = exObj["tube2"].string!
                    let tube3 = exObj["tube3"].string!
                    let reps = exObj["reps"].int!
                    let actualReps = exObj["actualReps"].int!
                    let restTime = exObj["restTime"].int!
                    let exReps = exObj["exReps"].int!
                    let exTime = exObj["exTime"].int!
                    
                    var actualExTime = 0
                    if exObj["actualExTime"].exists() {
                        actualExTime = exObj["actualExTime"].int!
                    }
                    
                    let repsTimeBool = exObj["repsTimeBool"].bool!
                    
                    exercisesList.append(WorkoutEx.init(name: name,
                                                        img: img,
                                                        tube1: tube1,
                                                        tube2: tube2,
                                                        tube3: tube3,
                                                        reps: reps,
                                                        actualReps: actualReps,
                                                        restTime: restTime,
                                                        exReps: exReps,
                                                        exTime: exTime,
                                                        actualExTime: actualExTime,
                                                        repsTimeBool: repsTimeBool))
                    
                }
                
            }
            catch {
                print("Invalid JSON Format")
            }
            
            
        }
    
    }
    
    
}
