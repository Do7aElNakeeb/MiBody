//
//  Register2.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 3/14/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import UIKit

class Register2: UIViewController {

    @IBOutlet weak var maleBtn: UIButton!
    @IBOutlet weak var femaleBtn: UIButton!
    @IBOutlet weak var dobDP: UIDatePicker!
    @IBOutlet weak var reg2Btn: UIButton!
    
    var gender: String!
    var dob: String!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        gender = "male"
        reg2Btn.layer.cornerRadius = 25
        reg2Btn.clipsToBounds = true
        
    }
    
    @IBAction func reg2OnClick(_ sender: AnyObject) {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "MM/dd/yyyy"
        dob = dateFormatter.string(from: dobDP.date)
        print(dob)
        
        let userDefaults = UserDefaults.standard
        userDefaults.set(gender, forKey: "gender")
        userDefaults.set(dob, forKey: "dob")
        userDefaults.synchronize()

        
        let pageViewController = self.parent as! Register
        pageViewController.scrollToNextViewController()
    }
    
    @IBAction func maleBtnOnClick(_ sender: AnyObject) {
        maleBtn.setImage(UIImage(named: "male_on")?.withRenderingMode(.alwaysOriginal), for: .normal)
        femaleBtn.setImage(UIImage(named: "female_off")?.withRenderingMode(.alwaysOriginal), for: .normal)
        gender = "male"
        print(gender)

    }
    
    @IBAction func femaleBtnOnClick(_ sender: AnyObject) {
        maleBtn.setImage(UIImage(named: "male_off")?.withRenderingMode(.alwaysOriginal), for: .normal)
        femaleBtn.setImage(UIImage(named: "female_on")?.withRenderingMode(.alwaysOriginal), for: .normal)
        gender = "female"
        print(gender)

    }
    
}
