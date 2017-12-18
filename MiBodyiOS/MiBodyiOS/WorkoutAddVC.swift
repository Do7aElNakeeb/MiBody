//
//  WorkoutAddVC.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 12/11/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import UIKit
import FSPagerView

class WorkoutAddVC: UIViewController, UIGestureRecognizerDelegate {

    @IBOutlet weak var workoutExercisesFSPV: FSPagerView!
    @IBOutlet weak var exercisesCV: UICollectionView!
    
    var workoutExercises = Array<WorkoutEx>()
//    var workoutExercises : [WorkoutEx] = [WorkoutEx()
    var exercisesArr = Array<Exercise>()
    
    var dragAndDropManager : KDDragAndDropManager?
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        workoutExercises.append(WorkoutEx.init(name: "Drop here", img: "drop_icon", tube1: "R", tube2: "B", tube3: "R", reps: 1, restTime: 10, exReps: 0, exTime: 0, repsTimeBool: false))
        
        workoutExercises.append(WorkoutEx.init(name: "Chinup", img: "chinup", tube1: "R", tube2: "B", tube3: "R", reps: 1, restTime: 10, exReps: 1, exTime: 0, repsTimeBool: false))
        
        workoutExercises.append(WorkoutEx.init(name: "Pullup", img: "pullup", tube1: "R", tube2: "B", tube3: "R", reps: 1, restTime: 10, exReps: 2, exTime: 0, repsTimeBool: false))
        
        workoutExercises.append(WorkoutEx.init(name: "Squat", img: "squat", tube1: "R", tube2: "B", tube3: "R", reps: 1, restTime: 10, exReps: 5, exTime: 0, repsTimeBool: false))
        
        loadExercises()
        initWorkoutExConfigCV()
        
    
        self.addBackBtn(selector: #selector(onBackClick))
        
    }
    
    func addBackBtn(selector: Selector) {
        
        let backBtnV = UIView(frame: CGRect(x: 10, y: 25, width: 80, height: 25))
        
        let backArrowIV = UIImageView(image: UIImage(named: "arrow"))
        backArrowIV.frame = CGRect(x: 0, y: 0, width: 15, height: backBtnV.frame.height)
        backArrowIV.contentMode = .scaleAspectFit
        backArrowIV.tintColor(color: UIColor.black) // extension
        
        let backBtn = UIButton(frame: CGRect(x: backArrowIV.frame.maxX, y: 0, width: backBtnV.frame.width - backArrowIV.frame.width - 15, height: backBtnV.frame.height))
        backBtn.setTitle("Back", for: .normal)
        backBtn.setTitleColor(UIColor.black, for: .normal)
        backBtn.titleLabel?.textAlignment = .left
        backBtn.addTarget(self, action: selector, for: .touchUpInside)
        
        backBtnV.addSubview(backArrowIV)
        backBtnV.addSubview(backBtn)
        
        let backTap = UITapGestureRecognizer(target: self, action: #selector(onBackClick))
        backTap.delegate = self
        backBtnV.addGestureRecognizer(backTap)
        
        self.view.addSubview(backBtnV)
    }
    
    func onBackClick() {
        dismiss(animated: true, completion: nil)
    }
    

    func initWorkoutExConfigCV(){
        
        let cellSize = self.view.frame.width / 2.5
        
        self.workoutExercisesFSPV.register(WorkoutExConfigCVCell.self, forCellWithReuseIdentifier: "WorkoutExConfigCVCell")
        self.workoutExercisesFSPV.transformer = FSPagerViewTransformer(type: .linear)
        self.workoutExercisesFSPV.itemSize = CGSize(width: cellSize, height: workoutExercisesFSPV.frame.height)

//        self.dragAndDropManager = KDDragAndDropManager(canvas: self.view, collectionViews: [workoutExercisesFSPV, exercisesCV])
        
        
    }
    
    func loadExercises(){
        
        for i in 0..<Exercises.exercises_names.count {
            
            exercisesArr.append(Exercise.init(name: Exercises.exercises_names[i], icon: Exercises.exercises_icons[i],
                                             img: Exercises.exercises_img[i], gif: Exercises.exercises_gifs[i],
                                             description: Exercises.exercises_discriptions[i]))
            
            print("Ex Added \(i)")
            
        }
        
    }
    
}


extension WorkoutAddVC: FSPagerViewDelegate, FSPagerViewDataSource, WorkoutExConfigCellDelegate {
 
    
    // MARK:- FSPagerViewDataSource
    
    public func numberOfItems(in pagerView: FSPagerView) -> Int {
        return workoutExercises.count
    }
    
    public func pagerView(_ pagerView: FSPagerView, cellForItemAt index: Int) -> FSPagerViewCell {

        let cell = pagerView.dequeueReusableCell(withReuseIdentifier: "WorkoutExConfigCVCell", at: index) as! WorkoutExConfigCVCell
        cell.exCountLbl.text = "\(workoutExercises[index].exReps)"
        cell.iconIV = UIImage(named: workoutExercises[index].img)!
        cell.nameLbl.text = workoutExercises[index].name
        cell.indexLbl.text = "\(index + 1)"
        cell.exBtn.setImage(UIImage(named: workoutExercises[index].img)?.withRenderingMode(.alwaysTemplate), for: .normal)
        cell.backgroundColor = UIColor.clear
        
        if workoutExercises[index].exReps == 0 {
            cell.countV.isHidden = true
            print("hide")
        }
        else {
            cell.countV.isHidden = false
            print("show")
        }
        
        cell.workoutExConfigCellDelegate = self
        return cell
    }
    
    func pagerView(_ pagerView: FSPagerView, didSelectItemAt index: Int) {
        print("index is: \(index)")
        pagerView.deselectItem(at: index, animated: true)
        pagerView.scrollToItem(at: index, animated: true)
    }
    
    func selectItemAt(index: Int) {
        print("indexasis: \(index)")
        self.workoutExercisesFSPV.deselectItem(at: index, animated: true)
        self.workoutExercisesFSPV.scrollToItem(at: index, animated: true)
    }
    
}


//extension WorkoutAddVC: KDDragAndDropCollectionViewDataSource {
//
//    func collectionView(_ collectionView: UICollectionView, indexPathForDataItem dataItem: AnyObject) -> IndexPath? {
//
//        if let candidate : WorkoutEx = dataItem as? WorkoutEx {
//
//            for item : WorkoutEx in workoutExercises[0] {
//                if candidate  == item {
//
//                    let position = data[collectionView.tag].index(of: item)! // ! if we are inside the condition we are guaranteed a position
//                    let indexPath = IndexPath(item: position, section: 0)
//                    return indexPath
//                }
//            }
//        }
//
//        return nil
//
//    }
//
//    func collectionView(_ collectionView: UICollectionView, dataItemForIndexPath indexPath: IndexPath) -> AnyObject {
//        <#code#>
//    }
//
//    func collectionView(_ collectionView: UICollectionView, moveDataItemFromIndexPath from: IndexPath, toIndexPath to: IndexPath) {
//        <#code#>
//    }
//
//    func collectionView(_ collectionView: UICollectionView, insertDataItem dataItem: AnyObject, atIndexPath indexPath: IndexPath) {
//        <#code#>
//    }
//
//    func collectionView(_ collectionView: UICollectionView, deleteDataItemAtIndexPath indexPath: IndexPath) {
//        <#code#>
//    }
//
//    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
//        <#code#>
//    }
//
//    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
//        <#code#>
//    }
//
//}

