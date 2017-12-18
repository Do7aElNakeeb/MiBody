//
//  ExercisesPageVC.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 12/10/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import UIKit

class ExercisesPageVC: UIViewController {
    
    var exercisesCV: UICollectionView!
    
//    var exercisesArr: Array<Exercise> = [Exercise.init(name: Exercises.exercises_names[0],
//                                                       icon: Exercises.exercises_icons[0],
//                                                       img: Exercises.exercises_img[0],
//                                                       gif: Exercises.exercises_gifs[0],
//                                                       description: Exercises.exercises_discreptions[0]),
//
//                                         Exercise.init(name: Exercises.exercises_names[1],
//                                                       icon: Exercises.exercises_icons[1],
//                                                       img: Exercises.exercises_img[1],
//                                                       gif: Exercises.exercises_gifs[1],
//                                                       description: Exercises.exercises_discreptions[1])
//                                         ]
    
    var exercisesArr = Array<Exercise>()
    var index: Int?

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        initExercisesCV()
        
    }
    
    func initExercisesCV(){
        
        let itemSize = self.view.frame.width / 3
        
        let flowLayout = UICollectionViewFlowLayout()
        
        flowLayout.itemSize = CGSize(width: itemSize,height: itemSize)
        
        flowLayout.minimumInteritemSpacing = 0
        flowLayout.minimumLineSpacing = (self.view.frame.height - 110 - itemSize * 3) / 3
        
        exercisesCV = UICollectionView(frame: CGRect(x: 0, y: 10, width: self.view.frame.width, height: self.view.frame.height - 20), collectionViewLayout: flowLayout)
        exercisesCV.setCollectionViewLayout(flowLayout, animated: true)
        exercisesCV.register(ExerciseCVCell.self, forCellWithReuseIdentifier: "ExerciseCell")
        exercisesCV.dataSource = self
        exercisesCV.delegate = self
        exercisesCV.allowsSelection = true
        exercisesCV.backgroundColor = UIColor.clear
        self.view.addSubview(exercisesCV)
        
        print(exercisesArr[0].name)
        
        
    }

}

extension ExercisesPageVC: UICollectionViewDelegate, UICollectionViewDataSource {
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return exercisesArr.count
    }
    
    func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "ExerciseCell", for: indexPath) as! ExerciseCVCell
        
        cell.iconIV.image = UIImage(named: exercisesArr[indexPath.row].icon)
        cell.nameLbl.text = exercisesArr[indexPath.row].name
        
        print(exercisesArr[indexPath.row].name)
        return cell
    }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        
        collectionView.deselectItem(at: indexPath, animated: true)
        
        let vc = self.storyboard?.instantiateViewController(withIdentifier: "ExerciseDetailsVC") as? ExerciseDetailsVC
        vc?.exercise = exercisesArr[indexPath.row]
        self.present(vc!, animated: true, completion: nil)
    }
}

