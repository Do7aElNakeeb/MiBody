//
//  LandingSlidingMenu.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 3/17/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import UIKit

class LandingSlidingMenu: UIViewController {

    @IBOutlet weak var slidingView: UIView!
    @IBOutlet weak var exercisesBtn: UIButton!
    @IBOutlet weak var routineBtn: UIButton!
    @IBOutlet weak var profileBtn: UIButton!
    @IBOutlet weak var settingsBtn: UIButton!
    @IBOutlet weak var followView: UIView!
    @IBOutlet weak var siteBtn: UIButton!
    @IBOutlet weak var fbBtn: UIButton!
    @IBOutlet weak var twitterBtn: UIButton!
    @IBOutlet weak var instaBtn: UIButton!
    @IBOutlet weak var utubeBtn: UIButton!
    
    let fullView: CGFloat = 230
    var partialView: CGFloat {
        return UIScreen.main.bounds.height - 150
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        roundBtns()
        
        let gesture = UIPanGestureRecognizer.init(target: self, action: #selector(panGesture))
        view.addGestureRecognizer(gesture)
        
    }
    
    override func viewDidAppear(_ animated: Bool) {
        
        UIView.animate(withDuration: 0.6, animations: { [weak self] in
            let frame = self?.view.frame
            let yComponent = self?.partialView
            self?.view.frame = CGRect(x: 0, y: yComponent!, width: frame!.width, height: frame!.height - 100)
            })
    }
    
    @IBAction func exBtnOnCLick(_ sender: AnyObject) {
    }
    
    @IBAction func routineBtnOnClick(_ sender: AnyObject) {
    }
    
    @IBAction func profileBtnOnClick(_ sender: AnyObject) {
    }
    
    @IBAction func settingsBtnOnClick(_ sender: AnyObject) {
    }
    
    @IBAction func siteBtnOnClick(_ sender: AnyObject) {
        
        UIApplication.tryURL(urls: [
            "http://mibody.ch" // Website
            ])
    }
    
    @IBAction func fbBtnOnClick(_ sender: AnyObject) {
        
        UIApplication.tryURL(urls: [
            "fb://profile/mibody.ch", // App
            "http://www.facebook.com/mibody.ch" // Website if app fails
            ])
    }
    
    @IBAction func twitterBtnOnClick(_ sender: AnyObject) {
    }
    
    @IBAction func instaBtnOnClick(_ sender: AnyObject) {
    }
    
    @IBAction func utubeBtnOnCLick(_ sender: AnyObject) {
    }
    
    func panGesture(_ recognizer: UIPanGestureRecognizer) {
        
        let translation = recognizer.translation(in: self.view)
        let velocity = recognizer.velocity(in: self.view)
        let y = self.view.frame.minY
        if ( y + translation.y >= fullView) && (y + translation.y <= partialView ) {
            self.view.frame = CGRect(x: 0, y: y + translation.y, width: view.frame.width, height: view.frame.height)
            recognizer.setTranslation(CGPoint.zero, in: self.view)
        }
        
        if recognizer.state == .ended {
            var duration =  velocity.y < 0 ? Double((y - fullView) / -velocity.y) : Double((partialView - y) / velocity.y )
            
            duration = duration > 1.3 ? 1 : duration
            
            UIView.animate(withDuration: duration, delay: 0.0, options: [.allowUserInteraction], animations: {
                if  velocity.y >= 0 {
                    self.view.frame = CGRect(x: 0, y: self.partialView, width: self.view.frame.width, height: self.view.frame.height)
                } else {
                    self.view.frame = CGRect(x: 0, y: self.fullView, width: self.view.frame.width, height: self.view.frame.height)
                }
                
                }, completion: nil)
        }
    }
    
    func roundBtns(){
        exercisesBtn.layer.cornerRadius = 20
        exercisesBtn.clipsToBounds = true
        routineBtn.layer.cornerRadius = 20
        routineBtn.clipsToBounds = true
        profileBtn.layer.cornerRadius = 20
        profileBtn.clipsToBounds = true
        settingsBtn.layer.cornerRadius = 20
        settingsBtn.clipsToBounds = true
        followView.layer.cornerRadius = 20
        followView.clipsToBounds = true
    }

}

extension UIApplication {
    class func tryURL(urls: [String]) {
        let application = UIApplication.shared
        for url in urls {
            if application.canOpenURL(NSURL(string: url)! as URL) {
                application.open(URL(string: url)!, options: [:], completionHandler: nil)
                return
            }
        }
    }
}
