//
//  Landing.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 3/17/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import UIKit

class Landing: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        addBottomSheetView()
    }
    
    
    func addBottomSheetView(scrollable: Bool? = true) {
        let landingSlidingMenu = scrollable! ? LandingSlidingMenu() : UIViewController()
        
        self.addChildViewController(landingSlidingMenu)
        self.view.addSubview(landingSlidingMenu.view)
        landingSlidingMenu.didMove(toParentViewController: self)
        
        let height = view.frame.height
        let width  = view.frame.width
        landingSlidingMenu.view.frame = CGRect(x: 0, y: self.view.frame.maxY, width: width, height: height)
    }
    

}
