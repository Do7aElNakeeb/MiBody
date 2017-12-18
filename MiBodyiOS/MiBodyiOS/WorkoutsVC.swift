//
//  WorkoutsVC.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 12/9/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import UIKit

class WorkoutsVC: UIViewController, UIGestureRecognizerDelegate {

    @IBOutlet weak var chooseTypeLbl: UILabel!
    @IBOutlet weak var predefinedBtn: UIButton!
    @IBOutlet weak var predefinedLbl: UILabel!
    @IBOutlet weak var personalisedBtn: UIButton!
    @IBOutlet weak var personalisedLbl: UILabel!
    
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        
        predefinedBtn.imageView?.contentMode = .scaleAspectFit
        predefinedBtn.layer.cornerRadius = 20
        predefinedBtn.imageEdgeInsets.bottom = 20
        predefinedBtn.imageEdgeInsets.top = 20
        
        personalisedBtn.imageView?.contentMode = .scaleAspectFit
        personalisedBtn.layer.cornerRadius = 20
        personalisedBtn.imageEdgeInsets.top = 30
        personalisedBtn.imageEdgeInsets.bottom = 30
    
        
        self.addBackBtn(selector: #selector(onBackClick))
        
    }
    
    func addBackBtn(selector: Selector) {
        
        let backBtnV = UIView(frame: CGRect(x: 10, y: 25, width: 80, height: 25))
        
        let backArrowIV = UIImageView(image: UIImage(named: "arrow"))
        backArrowIV.frame = CGRect(x: 0, y: 0, width: 15, height: backBtnV.frame.height)
        backArrowIV.contentMode = .scaleAspectFit
        backArrowIV.tintColor(color: UIColor.black) // extension
        
        let backBtn = UIButton(frame: CGRect(x: backArrowIV.frame.maxX, y: 0, width: backBtnV.frame.width - backArrowIV.frame.width - 15, height: backBtnV.frame.height))
        backBtn.setTitle("Back", for: .normal)
        backBtn.setTitleColor(UIColor.black, for: .normal)
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

}
