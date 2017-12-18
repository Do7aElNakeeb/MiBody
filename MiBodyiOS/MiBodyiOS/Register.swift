//
//  Register.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 3/14/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import UIKit
import Firebase

class Register: UIPageViewController {

    weak var registerDelegate: RegisterDelegate?
    
    weak var pageControl: UIPageControl!
    
    
    fileprivate(set) lazy var orderedViewControllers: [UIViewController] = {
        // The view controllers will be shown in this order
        return [self.newColoredViewController("Register1"),
                self.newColoredViewController("Register2"),
                self.newColoredViewController("Register3")]
    }()
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        dataSource = self
        delegate = self
        
        if let initialViewController = orderedViewControllers.first {
            scrollToViewController(initialViewController)
        }
        
        registerDelegate?.tutorialPageViewController(self,
                                                     didUpdatePageCount: orderedViewControllers.count)

        
        //pageControl.addTarget(self, action: #selector(didChangePageControlValue), for: .valueChanged)
        
    }
    
    /**
     Scrolls to the next view controller.
     */
    func scrollToNextViewController() {
        if let visibleViewController = viewControllers?.first,
            let nextViewController = pageViewController(self,
                                                        viewControllerAfter: visibleViewController) {
            scrollToViewController(nextViewController)
        }
    }
    
    /**
     Scrolls to the view controller at the given index. Automatically calculates
     the direction.
     
     - parameter newIndex: the new index to scroll to
     */
    func scrollToViewController(index newIndex: Int) {
        if let firstViewController = viewControllers?.first,
            let currentIndex = orderedViewControllers.index(of: firstViewController) {
            let direction: UIPageViewControllerNavigationDirection = newIndex >= currentIndex ? .forward : .reverse
            let nextViewController = orderedViewControllers[newIndex]
            scrollToViewController(nextViewController, direction: direction)
        }
    }
    
    fileprivate func newColoredViewController(_ color: String) -> UIViewController {
        return UIStoryboard(name: "Main", bundle: nil) .
            instantiateViewController(withIdentifier: "\(color)")
    }
    
    /**
     Scrolls to the given 'viewController' page.
     
     - parameter viewController: the view controller to show.
     */
    fileprivate func scrollToViewController(_ viewController: UIViewController,
                                            direction: UIPageViewControllerNavigationDirection = .forward) {
        setViewControllers([viewController],
                           direction: direction,
                           animated: true,
                           completion: { (finished) -> Void in
                            // Setting the view controller programmatically does not fire
                            // any delegate methods, so we have to manually notify the
                            // 'tutorialDelegate' of the new index.
                            self.notifyTutorialDelegateOfNewIndex()
        })
    }
    
    /**
     Notifies '_tutorialDelegate' that the current page index was updated.
     */
    fileprivate func notifyTutorialDelegateOfNewIndex() {
        if let firstViewController = viewControllers?.first,
            let index = orderedViewControllers.index(of: firstViewController) {
            registerDelegate?.tutorialPageViewController(self,
                                                         didUpdatePageIndex: index)
        }
    }
    
    /**
     Fired when the user taps on the pageControl to change its current page.
     */
    func didChangePageControlValue() {
        scrollToViewController(index: pageControl.currentPage)
    }

    func handleFirebaseRegister() {
        
        let userDefaults = UserDefaults()
        let name: String = userDefaults.string(forKey: "name") ?? ""
        let email: String = userDefaults.string(forKey: "email") ?? ""
        let password: String = userDefaults.string(forKey: "password") ?? ""
        let gender: String = userDefaults.string(forKey: "gender") ?? ""
        let dob: String = userDefaults.string(forKey: "dob") ?? ""
        let weight: String = userDefaults.string(forKey: "weight") ?? ""
        let height: String = userDefaults.string(forKey: "height") ?? ""
        let BMI: String = userDefaults.string(forKey: "BMI") ?? ""
        let units: String = userDefaults.string(forKey: "units") ?? ""
        
        if name.isEmpty || email.isEmpty || password.isEmpty {
            print("Missing Fields!")
            
            scrollToViewController(index: 0)
            
            let alert = UIAlertController(title: "Error", message: "Missing Fields!", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
            self.present(alert, animated: true, completion: nil)

            return
        }
        
        if dob.isEmpty {
            print("Missing Fields!")
            
            scrollToViewController(index: 1)
            
            let alert = UIAlertController(title: "Error", message: "Missing Fields!", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
            self.present(alert, animated: true, completion: nil)
            
            return
        }
        
        if weight.isEmpty || height.isEmpty {
            print("Missing Fields!")
            
            scrollToViewController(index: 2)
            
            let alert = UIAlertController(title: "Error", message: "Missing Fields!", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
            self.present(alert, animated: true, completion: nil)
            
            return
        }
        
        FIRAuth.auth()?.createUser(withEmail: email, password: password, completion: { (user: FIRUser?, error) in
            
            if error != nil {
                let alert = UIAlertController(title: "Error", message: error as? String, preferredStyle: UIAlertControllerStyle.alert)
                alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
                self.present(alert, animated: true, completion: nil)
                return

            }
            
            guard let uid = user?.uid else {
                let alert = UIAlertController(title: "Error", message: "Failed to get User ID", preferredStyle: UIAlertControllerStyle.alert)
                alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
                self.present(alert, animated: true, completion: nil)
                return
            }
            
            //successfully authenticated user
            let ref = FIRDatabase.database().reference(fromURL: "https://mibody-159619.firebaseio.com/")
            let usersReference = ref.child("users").child(uid)
            let values = ["name": name, "email": email, "gender": gender, "dob": dob, "weight": weight, "height": height, "BMI": BMI, "units": units]
            usersReference.updateChildValues(values, withCompletionBlock: { (err, ref) in
                
                if err != nil {
                    let alert = UIAlertController(title: "Error", message: error as? String, preferredStyle: UIAlertControllerStyle.alert)
                    alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
                    self.present(alert, animated: true, completion: nil)
                    return
                }
                
                self.dismiss(animated: true, completion: nil)
            })
            
        })
    }
    
}



// MARK: UIPageViewControllerDataSource

extension Register: UIPageViewControllerDataSource {
    
    func pageViewController(_ pageViewController: UIPageViewController,
                            viewControllerBefore viewController: UIViewController) -> UIViewController? {
        guard let viewControllerIndex = orderedViewControllers.index(of: viewController) else {
            return nil
        }
        
        let previousIndex = viewControllerIndex - 1
        
        // User is on the first view controller and swiped left to loop to
        // the last view controller.
        guard previousIndex >= 0 else {
            return orderedViewControllers.last
        }
        
        guard orderedViewControllers.count > previousIndex else {
            return nil
        }
        
        return orderedViewControllers[previousIndex]
    }
    
    func pageViewController(_ pageViewController: UIPageViewController,
                            viewControllerAfter viewController: UIViewController) -> UIViewController? {
        guard let viewControllerIndex = orderedViewControllers.index(of: viewController) else {
            return nil
        }
        
        let nextIndex = viewControllerIndex + 1
        let orderedViewControllersCount = orderedViewControllers.count
        
        // User is on the last view controller and swiped right to loop to
        // the first view controller.
        guard orderedViewControllersCount != nextIndex else {
            return orderedViewControllers.first
        }
        
        guard orderedViewControllersCount > nextIndex else {
            return nil
        }
        
        return orderedViewControllers[nextIndex]
    }
    
}

extension Register: UIPageViewControllerDelegate {
    
    func pageViewController(_ pageViewController: UIPageViewController,
                            didFinishAnimating finished: Bool,
                            previousViewControllers: [UIViewController],
                            transitionCompleted completed: Bool) {
        notifyTutorialDelegateOfNewIndex()
    }
    
}

protocol RegisterDelegate: class {
    
    /**
     Called when the number of pages is updated.
     
     - parameter tutorialPageViewController: the TutorialPageViewController instance
     - parameter count: the total number of pages.
     */
    func tutorialPageViewController(_ tutorialPageViewController: Register,
                                    didUpdatePageCount count: Int)
    
    /**
     Called when the current index is updated.
     
     - parameter tutorialPageViewController: the TutorialPageViewController instance
     - parameter index: the index of the currently visible page.
     */
    func tutorialPageViewController(_ tutorialPageViewController: Register,
                                    didUpdatePageIndex index: Int)
    
}
