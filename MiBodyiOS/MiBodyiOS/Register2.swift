//
//  Register2.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 3/14/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import UIKit

class Register2: UIViewController, UIGestureRecognizerDelegate {

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

        let pageViewController = self.parent as! Register
        pageViewController.scrollToViewController(index: 0)
        
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
