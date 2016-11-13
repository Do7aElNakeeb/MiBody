//
//  Login.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 11/12/16.
//  Copyright © 2016 MiBody. All rights reserved.
//

import UIKit

class Login: UIViewController {
    
    @IBOutlet weak var email: UITextField!
    @IBOutlet weak var password: UITextField!
    
    @IBOutlet weak var loginBtn: UIButton!
    @IBOutlet weak var facebookBtn: UIButton!
    @IBOutlet weak var signupBtn: UIButton!
    @IBAction func loginBtn(sender: AnyObject) {
        
        
    }

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        email.layer.cornerRadius = 15
        password.layer.cornerRadius = 15
        loginBtn.layer.cornerRadius = 15
        loginBtn.clipsToBounds = true
        email.clipsToBounds = true
        password.clipsToBounds = true
        facebookBtn.layer.cornerRadius = 15
        facebookBtn.clipsToBounds = true
        signupBtn.layer.cornerRadius = 15
        signupBtn.clipsToBounds = true
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
