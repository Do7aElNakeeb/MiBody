//
//  ProfileVC.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 12/9/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import UIKit

class ProfileVC: UIViewController, UIGestureRecognizerDelegate {

    @IBOutlet weak var dimensionsBtn: UIButton!
    @IBOutlet weak var statsBtn: UIButton!
    @IBOutlet weak var profileInfoBtn: UIButton!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        dimensionsBtn.layer.cornerRadius = 20
        dimensionsBtn.imageEdgeInsets.top = 20
        dimensionsBtn.imageEdgeInsets.bottom = 20
        dimensionsBtn.imageView?.contentMode = .scaleAspectFit
        
        statsBtn.layer.cornerRadius = 20
        statsBtn.imageEdgeInsets.top = 20
        statsBtn.imageEdgeInsets.bottom = 20
        statsBtn.imageView?.contentMode = .scaleAspectFit
        
        profileInfoBtn.layer.cornerRadius = 20
        profileInfoBtn.imageEdgeInsets.top = 20
        profileInfoBtn.imageEdgeInsets.bottom = 20
        profileInfoBtn.imageView?.contentMode = .scaleAspectFit
    
        
        self.addBackBtn(selector: #selector(onBackClick))
        
    }
    
    func addBackBtn(selector: Selector) {
        
        let backBtnV = UIView(frame: CGRect(x: 10, y: 25, width: 80, height: 25))
        
        let backArrowIV = UIImageView(image: UIImage(named: "arrow"))
        backArrowIV.frame = CGRect(x: 0, y: 0, width: 15, height: backBtnV.frame.height)
        backArrowIV.contentMode = .scaleAspectFit
        backArrowIV.tintColor(color: UIColor.mibodyOrange) // extension
        
        let backBtn = UIButton(frame: CGRect(x: backArrowIV.frame.maxX, y: 0, width: backBtnV.frame.width - backArrowIV.frame.width - 15, height: backBtnV.frame.height))
        backBtn.setTitle("Back", for: .normal)
        backBtn.setTitleColor(UIColor.mibodyOrange, for: .normal)
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
