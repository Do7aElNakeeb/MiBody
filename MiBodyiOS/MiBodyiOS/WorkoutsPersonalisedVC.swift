//
//  WorkoutsPersonalisedVC.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 12/11/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import UIKit

class WorkoutsPersonalisedVC: UIViewController, UIGestureRecognizerDelegate {

    @IBOutlet weak var addWorkoutBtn: UIButton!
    @IBOutlet weak var workoutsTV: UITableView!
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        initViews()
    }
    
    func initViews() {
        
        addWorkoutBtn.layer.cornerRadius = 50
    
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

extension WorkoutsPersonalisedVC: UITableViewDataSource, UITableViewDelegate {
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        
        let cell = UITableViewCell()
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return 0
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        
        tableView.deselectRow(at: indexPath, animated: true)
    }
}
