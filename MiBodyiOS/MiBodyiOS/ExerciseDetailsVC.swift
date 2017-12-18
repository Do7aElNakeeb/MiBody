//
//  ExerciseDetailsVC.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 12/10/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import UIKit

class ExerciseDetailsVC: UIViewController, UIGestureRecognizerDelegate {

    @IBOutlet weak var exNameLbl: UILabel!
    @IBOutlet weak var exImgIV: UIImageView!
    
    var viewPager:ViewPagerController!
    
    var exercise = Exercise()
    
    var tabs = [
        ViewPagerTab(title: "Exercise", image: UIImage(named: "")),
        ViewPagerTab(title: "Description", image: UIImage(named: ""))
    ]
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
    
        setupViewPager()
        
        exNameLbl.text = exercise.name
        exImgIV.image = UIImage(named: exercise.img)
        exImgIV.contentMode = .scaleAspectFit
        exImgIV.layer.cornerRadius = 30
        
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
    
    
    func setupViewPager (){
        
        let options = ViewPagerOptions(viewPagerWithFrame: CGRect(x: 0, y: exImgIV.frame.maxY + 20, width: self.view.frame.width, height: self.view.frame.height - exImgIV.frame.maxY - 20))
        
        options.tabType = ViewPagerTabType.basic
        
        options.tabViewBackgroundDefaultColor = UIColor.clear
        options.tabViewBackgroundHighlightColor = UIColor.clear
        options.tabViewTextDefaultColor = UIColor.black
        options.tabViewTextHighlightColor = UIColor.black
        options.tabIndicatorViewBackgroundColor = UIColor.mibodyOrange
        options.isEachTabEvenlyDistributed = true
        options.fitAllTabsInView = true
        options.tabViewTextFont = UIFont.systemFont(ofSize: 17)
        options.tabViewPaddingLeft = 20
        options.tabViewPaddingRight = 20
        options.isTabHighlightAvailable = true
        
        viewPager = ViewPagerController()
        viewPager.options = options
        viewPager.dataSource = self
        viewPager.delegate = self
        
        self.addChildViewController(viewPager)
        self.view.addSubview(viewPager.view)
        viewPager.didMove(toParentViewController: self)
        
    }

}

extension ExerciseDetailsVC: ViewPagerControllerDataSource {

    func numberOfPages() -> Int {
        return 2
    }

    func viewControllerAtPosition(position:Int) -> UIViewController {
        if position == 0 {
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "ExerciseDetailsGifVC") as! ExerciseDetailsGifVC
            vc.gif = exercise.gif
            
            return vc
        }
        else {
            let vc = self.storyboard?.instantiateViewController(withIdentifier: "ExerciseDetailsDescVC") as! ExerciseDetailsDescVC
            vc.descriptionTxt = exercise.description
            
            print(exercise.description)
            return vc
        }
    }

    func tabsForPages() -> [ViewPagerTab] {
        return tabs
    }

    func startViewPagerAtIndex() -> Int {
        return 0
    }
}

extension ExerciseDetailsVC: ViewPagerControllerDelegate {

    func willMoveToControllerAtIndex(index:Int) {
        print("Moving to page \(index)")
    }

    func didMoveToControllerAtIndex(index: Int) {
        print("Moved to page \(index)")
    }
}

