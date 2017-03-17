//
//  Register1.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 3/14/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import UIKit

class Register1: UIViewController {

    @IBOutlet weak var ppEditBtn: UIButton!
    @IBOutlet weak var nameTxtField: UITextField!
    @IBOutlet weak var emailTxtField: UITextField!
    @IBOutlet weak var passwordTxtField: UITextField!
    @IBOutlet weak var reg1Btn: UIButton!
    
    var register: Register!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        
        ppEditBtn.layer.cornerRadius = 25
        ppEditBtn.clipsToBounds = true
        
        ppEditBtn.imageView?.transform = CGAffineTransform(scaleX: -1, y: 1)
        ppEditBtn.imageEdgeInsets = UIEdgeInsets(top: 10, left: 15, bottom: 10, right: 15)
        
        nameTxtField.attributedPlaceholder = NSAttributedString(string: "Name", attributes: [NSForegroundColorAttributeName: UIColor.gray])
        
        emailTxtField.attributedPlaceholder = NSAttributedString(string: "Email", attributes: [NSForegroundColorAttributeName: UIColor.gray])
        
        passwordTxtField.attributedPlaceholder = NSAttributedString(string: "Password", attributes: [NSForegroundColorAttributeName: UIColor.gray])
        
        reg1Btn.layer.cornerRadius = 25
        reg1Btn.clipsToBounds = true
    }

    @IBAction func reg1OnClick(_ sender: AnyObject) {
        if nameTxtField.text == ""{
            let alert = UIAlertController(title: "Alert", message: "Name is missing", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "Edit", style: UIAlertActionStyle.default, handler: nil))
            self.present(alert, animated: true, completion: nil)
            return
        }
        if emailTxtField.text == ""{
            let alert = UIAlertController(title: "Alert", message: "Email is missing", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "Edit", style: UIAlertActionStyle.default, handler: nil))
            self.present(alert, animated: true, completion: nil)
            return
            
        }
        if passwordTxtField.text == ""{
            let alert = UIAlertController(title: "Alert", message: "Password is missing", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "Edit", style: UIAlertActionStyle.default, handler: nil))
            self.present(alert, animated: true, completion: nil)
            return
        }
        
        let userDefaults = UserDefaults.standard
        userDefaults.set(nameTxtField.text, forKey: "name")
        userDefaults.set(emailTxtField.text, forKey: "email")
        userDefaults.set(passwordTxtField.text, forKey: "password")
        userDefaults.synchronize()
        
        let pageViewController = self.parent as! Register
        pageViewController.scrollToNextViewController()

        //register?.setViewControllers([self], direction: UIPageViewControllerNavigationDirection.forward, animated: true, completion: nil)
    }
}


