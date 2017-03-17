//
//  Register3.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 3/14/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import UIKit

class Register3: UIViewController {

    @IBOutlet weak var no1Lbl: UILabel!
    @IBOutlet weak var no2Lbl: UILabel!
    @IBOutlet weak var no3Lbl: UILabel!
    @IBOutlet weak var unitsView: UIView!
    @IBOutlet weak var cmBtn: UIButton!
    @IBOutlet weak var kgBtn: UIButton!
    @IBOutlet weak var inchBtn: UIButton!
    @IBOutlet weak var lbBtn: UIButton!
    @IBOutlet weak var weightTxtField: UITextField!
    @IBOutlet weak var heightTxtField: UITextField!
    @IBOutlet weak var BMILbl: UILabel!
    @IBOutlet weak var BMITxt: UILabel!
    @IBOutlet weak var reg3Btn: UIButton!
    
    var units: String = "0"
    var BMI: String!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        BMILbl.isHidden = true
        BMITxt.isHidden = true
        
        no1Lbl.layer.cornerRadius = 25
        no1Lbl.clipsToBounds = true
        no2Lbl.layer.cornerRadius = 25
        no2Lbl.clipsToBounds = true
        no3Lbl.layer.cornerRadius = 25
        no3Lbl.clipsToBounds = true
        no3Lbl.layer.borderColor = UIColor(red: 252/255, green: 61/255, blue: 57/255, alpha: 1.00).cgColor
        no3Lbl.layer.borderWidth = 3.0
        
        unitsView.layer.cornerRadius = 30
        unitsView.clipsToBounds = true
        
        weightTxtField.layer.cornerRadius = 15
        weightTxtField.clipsToBounds = true
        heightTxtField.layer.cornerRadius = 15
        weightTxtField.clipsToBounds = true
        
        
        weightTxtField.backgroundColor = UIColor.white
        weightTxtField.layer.borderColor = UIColor(red: 252/255, green: 61/255, blue: 57/255, alpha: 1.00).cgColor

        weightTxtField.layer.borderWidth = 1.5
        
        heightTxtField.backgroundColor = UIColor.white
        heightTxtField.layer.borderColor = UIColor(red: 252/255, green: 61/255, blue: 57/255, alpha: 1.00).cgColor

        heightTxtField.layer.borderWidth = 1.5
        
        
        reg3Btn.layer.cornerRadius = 25
        reg3Btn.clipsToBounds = true
        
        weightTxtField.addTarget(self, action: #selector(BMIeqn), for: .editingChanged)
        heightTxtField.addTarget(self, action: #selector(BMIeqn), for: .editingChanged)
        
        cmBtn.addTarget(self, action: #selector(changeUnitsTO0), for: .touchUpInside)
        kgBtn.addTarget(self, action: #selector(changeUnitsTO0), for: .touchUpInside)
        inchBtn.addTarget(self, action: #selector(changeUnitsTO1), for: .touchUpInside)
        lbBtn.addTarget(self, action: #selector(changeUnitsTO1), for: .touchUpInside)

    }
    
    @IBAction func reg3OnClick(_ sender: AnyObject) {
        
        if weightTxtField.text == ""{
            let alert = UIAlertController(title: "Alert", message: "Weight is missing", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "Edit", style: UIAlertActionStyle.default, handler: nil))
            self.present(alert, animated: true, completion: nil)
            return
        }
        if heightTxtField.text == ""{
            let alert = UIAlertController(title: "Alert", message: "Height is missing", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "Edit", style: UIAlertActionStyle.default, handler: nil))
            self.present(alert, animated: true, completion: nil)
            return
            
        }
        
        let userDefaults = UserDefaults.standard
        userDefaults.set(weightTxtField.text, forKey: "weight")
        userDefaults.set(heightTxtField.text, forKey: "height")
        userDefaults.set(BMI, forKey: "BMI")
        userDefaults.set(units, forKey: "units")
        userDefaults.synchronize()
        
        let pageViewController = self.parent as! Register
        pageViewController.handleFirebaseRegister()
    }
    
    func changeUnitsTO0(){
        cmBtn.titleLabel?.textColor = UIColor(red: 252/255, green: 61/255, blue: 57/255, alpha: 1.00)
        kgBtn.titleLabel?.textColor = UIColor(red: 252/255, green: 61/255, blue: 57/255, alpha: 1.00)
        inchBtn.titleLabel?.textColor = UIColor(red: 0, green: 0, blue: 0, alpha: 1.00)
        lbBtn.titleLabel?.textColor = UIColor(red: 0, green: 0, blue: 0, alpha: 1.00)
        units = "0"
    }
    
    func changeUnitsTO1(){
        inchBtn.titleLabel?.textColor = UIColor(red: 252/255, green: 61/255, blue: 57/255, alpha: 1.00)
        lbBtn.titleLabel?.textColor = UIColor(red: 252/255, green: 61/255, blue: 57/255, alpha: 1.00)
        cmBtn.titleLabel?.textColor = UIColor(red: 0, green: 0, blue: 0, alpha: 1.00)
        kgBtn.titleLabel?.textColor = UIColor(red: 0, green: 0, blue: 0, alpha: 1.00)
        units = "1"
    }
    
    func BMIeqn(){
    
        if  (heightTxtField.text?.isEmpty)! || (weightTxtField.text?.isEmpty)! {
            BMILbl.isHidden = true
            BMITxt.isHidden = true
            return
        }
        
        BMILbl.isHidden = false
        BMITxt.isHidden = false
        
        let height = Float(heightTxtField.text!)!
        let weight = Float(weightTxtField.text!)!
        
        if units == "0" {
            BMI = String(format:"%.2f", weight / (height * height / 10000))
        }
        else {
            BMI = String(format:"%.2f", 703 * (weight / (height * height / 10000)))
        }
        
        BMITxt.text = BMI
    }

}
