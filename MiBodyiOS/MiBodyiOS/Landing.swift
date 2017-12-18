//
//  Landing.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 3/17/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import UIKit
import ISHPullUp

class Landing: UIViewController {

    open var blackV = UIView()
    var landingSlidingMenu = UIViewController()
    
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        blackV.frame = CGRect(x: 0, y: 0, width: self.view.frame.width, height: self.view.frame.height)
        blackV.backgroundColor = UIColor.black
        blackV.alpha = 0.3
        blackV.isHidden = true
        
//        self.view.addSubview(blackV)
        
//        addBottomSheetView()
        
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
//        addBottomSheetView()
    }
    
    
    func addBottomSheetView(scrollable: Bool? = true) {
        landingSlidingMenu = scrollable! ? LandingSlidingMenu() : UIViewController()
        
        self.addChildViewController(landingSlidingMenu)
        self.view.addSubview(landingSlidingMenu.view)
        landingSlidingMenu.didMove(toParentViewController: self)
        
        let height = view.frame.height
        let width  = view.frame.width
        landingSlidingMenu.view.frame = CGRect(x: 0, y: self.view.frame.maxY, width: width, height: height)
    }
    
    override func viewWillDisappear(_ animated: Bool) {
        super.viewWillDisappear(animated)
        
//        landingSlidingMenu.removeFromParentViewController()
        
    }

}

extension Landing: ISHPullUpContentDelegate {
    
    // MARK: ISHPullUpContentDelegate
    
    func pullUpViewController(_ vc: ISHPullUpViewController, update edgeInsets: UIEdgeInsets, forContentViewController _: UIViewController) {
        if #available(iOS 11.0, *) {
            additionalSafeAreaInsets = edgeInsets
            view.layoutMargins = .zero
        } else {
            // update edgeInsets
            view.layoutMargins = edgeInsets
        }
        
        // call layoutIfNeeded right away to participate in animations
        // this method may be called from within animation blocks
        view.layoutIfNeeded()
    }
}

