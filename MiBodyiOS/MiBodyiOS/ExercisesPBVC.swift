//
//  ExercisesPBVC.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 12/10/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import UIKit
import Pageboy

class ExercisesPBVC: PageboyViewController {

    
    var exercisesPagesVCs = [UIViewController]()
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        dataSource = self
        delegate = self
        
        loadExercises()
    }

    func loadExercises(){
        
        var tempExercisesArr = Array<Exercise>()
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)

        
        for i in 0..<Exercises.exercises_names.count + 1 {
            
            if ((i % 9 == 0) || i == Exercises.exercises_names.count) && i != 0 {
                
                let exercisesPageVC = storyboard.instantiateViewController(withIdentifier: "exercisesPageVC") as! ExercisesPageVC
                exercisesPageVC.index = exercisesPagesVCs.count
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
        
        reloadPages()
        
    }

}


extension ExercisesPBVC: PageboyViewControllerDataSource {
    
    func numberOfViewControllers(in pageboyViewController: PageboyViewController) -> Int {
        print("exCount: \(exercisesPagesVCs.count)")
        return exercisesPagesVCs.count
    }
    
    func viewController(for pageboyViewController: PageboyViewController,
                        at index: PageboyViewController.PageIndex) -> UIViewController? {
        
        print("exPageIndex: \(index)")
        return exercisesPagesVCs[index]
    }
    
    func defaultPage(for pageboyViewController: PageboyViewController) -> PageboyViewController.Page? {
        return nil
    }
}

// MARK: PageboyViewControllerDelegate
extension ExercisesPBVC: PageboyViewControllerDelegate {
    
    func pageboyViewController(_ pageboyViewController: PageboyViewController,
                               willScrollToPageAt index: Int,
                               direction: PageboyViewController.NavigationDirection,
                               animated: Bool) {
        //        print("willScrollToPageAtIndex: \(index)")
    }
    
    func pageboyViewController(_ pageboyViewController: PageboyViewController,
                               didScrollTo position: CGPoint,
                               direction: PageboyViewController.NavigationDirection,
                               animated: Bool) {
        //        print("didScrollToPosition: \(position)")
        
        
        //        self.updateBarButtonStates(index: pageboyViewController.currentIndex ?? 0)
    }
    
    func pageboyViewController(_ pageboyViewController: PageboyViewController,
                               didScrollToPageAt index: Int,
                               direction: PageboyViewController.NavigationDirection,
                               animated: Bool) {
        //        print("didScrollToPageAtIndex: \(index)")
        
    }
    
    func pageboyViewController(_ pageboyViewController: PageboyViewController,
                               didReloadWith currentViewController: UIViewController,
                               currentPageIndex: PageboyViewController.PageIndex) {
        
    }
}
