//
//  ExerciseDetailsDescVC.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 12/10/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import UIKit

class ExerciseDetailsDescVC: UIViewController {

    @IBOutlet weak var exDescTV: UITextView!
    
    var descriptionTxt = ""
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        exDescTV.text = descriptionTxt
        
    }



}
