//
//  ExercisesVC.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 12/9/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import UIKit

class ExercisesVC: UIViewController, UIGestureRecognizerDelegate {

    @IBOutlet weak var chooseExLbl: UILabel!
    @IBOutlet weak var exercisePC: UIPageControl!
    @IBOutlet weak var exercisesPagerV: UIView!
    
    var exercisesPagesVCs = [UIViewController]()
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        exercisesPagerV.frame = CGRect(x: 0, y: chooseExLbl.frame.maxY + 20, width: view.frame.width, height: exercisePC.frame.minY - chooseExLbl.frame.maxY - 40)
        
//        loadExercises()
//        setupViewPager()
        
        
        self.addBackBtn(selector: #selector(onBackClick))
        
    }
    
    func addBackBtn(selector: Selector) {
        
        let backBtnV = UIView(frame: CGRect(x: 10, y: 25, width: 80, height: 25))
        
        let backArrowIV = UIImageView(image: UIImage(named: "arrow"))
        backArrowIV.frame = CGRect(x: 0, y: 0, width: 15, height: backBtnV.frame.height)
        backArrowIV.contentMode = .scaleAspectFit
        backArrowIV.tintColor(color: UIColor.white) // extension
        
        let backBtn = UIButton(frame: CGRect(x: backArrowIV.frame.maxX, y: 0, width: backBtnV.frame.width - backArrowIV.frame.width - 15, height: backBtnV.frame.height))
        backBtn.setTitle("Back", for: .normal)
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

 

    
    func loadExercises(){

        var tempExercisesArr = Array<Exercise>()
    
        for i in 0..<Exercises.exercises_names.count + 1 {
            
            if ((i % 9 == 0) || i == Exercises.exercises_names.count) && i != 0 {
                
                let exercisesPageVC = ExercisesPageVC()
                    
                exercisesPageVC.exercisesArr = tempExercisesArr
                exercisesPagesVCs.append(exercisesPageVC)
                tempExercisesArr.removeAll()
                print("ExPage added \(i)")
            }
            
            if (i != Exercises.exercises_names.count) {
                
                tempExercisesArr.append(Exercise.init(name: Exercises.exercises_names[i], icon: Exercises.exercises_icons[i],
                                                      img: Exercises.exercises_img[i], gif: Exercises.exercises_gifs[i],
                                                      description: Exercises.exercises_discriptions[i]))
                
                print("Ex Added \(i)")
            }
           
        }
        
    }
    
}




