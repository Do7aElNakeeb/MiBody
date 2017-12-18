//
//  LandingSlidingMenu.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 3/17/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import UIKit
import ISHPullUp

class LandingSlidingMenu: UIViewController {

    @IBOutlet weak var slidingView: ISHPullUpHandleView!
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
    
    
    var firstAppearanceCompleted = false
    weak var pullUpController: ISHPullUpViewController!
    
    // we allow the pullUp to snap to the half way point
    private var halfWayPoint = CGFloat(0)
    
    let fullView: CGFloat = 230
    var partialView: CGFloat {
        return UIScreen.main.bounds.height - 150
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        roundBtns()
        
//        let gesture = UIPanGestureRecognizer.init(target: self, action: #selector(panGesture))
//        view.addGestureRecognizer(gesture)
//
//        let landingVC = self.parent as! Landing
//        landingVC.blackV.isHidden = true
        
        let tapGesture = UITapGestureRecognizer(target: self, action: #selector(handleTapGesture))
        view.addGestureRecognizer(tapGesture)
        
    }
    
    override func viewDidAppear(_ animated: Bool) {
        
//        UIView.animate(withDuration: 0, animations: { [weak self] in
//            let frame = self?.view.frame
//            let yComponent = self?.partialView
//            self?.view.frame = CGRect(x: 0, y: yComponent!, width: frame!.width, height: frame!.height - 100)
//            })
        
        firstAppearanceCompleted = true
        
    }
    
    private dynamic func handleTapGesture(gesture: UITapGestureRecognizer) {
        if pullUpController.isLocked {
            return
        }
        
        pullUpController.toggleState(animated: true)
    }
    
    @IBAction func exBtnOnCLick(_ sender: AnyObject) {
        
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
        let vc = storyboard.instantiateViewController(withIdentifier: "ExercisesVC") as? ExercisesVC
        self.present(vc!, animated: true, completion: nil)
        
//        self.navigationController?.pushViewController(vc!, animated: true)
        
    }
    
    @IBAction func routineBtnOnClick(_ sender: AnyObject) {
        
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
        let vc = storyboard.instantiateViewController(withIdentifier: "WorkoutsVC") as? WorkoutsVC
        self.present(vc!, animated: true, completion: nil)
    }
    
    @IBAction func profileBtnOnClick(_ sender: AnyObject) {
        
        let storyboard = UIStoryboard(name: "Main", bundle: Bundle.main)
        let vc = storyboard.instantiateViewController(withIdentifier: "ProfileVC") as? ProfileVC
        self.present(vc!, animated: true, completion: nil)
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
                
                let landingVC = self.parent as! Landing
                
                if  velocity.y >= 0 {
                    self.view.frame = CGRect(x: 0, y: self.partialView, width: self.view.frame.width, height: self.view.frame.height)
                    
                    // TODO hide with duration
                    landingVC.blackV.isHidden = true
                    
                } else {
                    self.view.frame = CGRect(x: 0, y: self.fullView, width: self.view.frame.width, height: self.view.frame.height)
                    
                    landingVC.blackV.isHidden = false
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

extension LandingSlidingMenu: ISHPullUpSizingDelegate, ISHPullUpStateDelegate {
    
    // MARK: ISHPullUpSizingDelegate
    
    func pullUpViewController(_ pullUpViewController: ISHPullUpViewController, maximumHeightForBottomViewController bottomVC: UIViewController, maximumAvailableHeight: CGFloat) -> CGFloat {
//        let totalHeight = rootView.systemLayoutSizeFitting(UILayoutFittingCompressedSize).height
        
        // we allow the pullUp to snap to the half way point
        // we "calculate" the cached value here
        // and perform the snapping in ..targetHeightForBottomViewController..
//        halfWayPoint = totalHeight / 2.0
        return 430
    }
    
    func pullUpViewController(_ pullUpViewController: ISHPullUpViewController, minimumHeightForBottomViewController bottomVC: UIViewController) -> CGFloat {
        return 150 //topView.systemLayoutSizeFitting(UILayoutFittingCompressedSize).height;
    }
    
    func pullUpViewController(_ pullUpViewController: ISHPullUpViewController, targetHeightForBottomViewController bottomVC: UIViewController, fromCurrentHeight height: CGFloat) -> CGFloat {
        // if around 30pt of the half way point -> snap to it
//        if abs(height - halfWayPoint) < 30 {
//            return halfWayPoint
//        }
        
        // default behaviour
        return 430
    }
    
    func pullUpViewController(_ pullUpViewController: ISHPullUpViewController, update edgeInsets: UIEdgeInsets, forBottomViewController bottomVC: UIViewController) {
        // we update the scroll view's content inset
        // to properly support scrolling in the intermediate states
//        scrollView.contentInset = edgeInsets;
    }
    
    // MARK: ISHPullUpStateDelegate
    
    func pullUpViewController(_ pullUpViewController: ISHPullUpViewController, didChangeTo state: ISHPullUpState) {
//        topLabel.text = textForState(state);
        slidingView.setState(ISHPullUpHandleView.handleState(for: state), animated: firstAppearanceCompleted)
        
        // Hide the scrollview in the collapsed state to avoid collision
        // with the soft home button on iPhone X
//        UIView.animate(withDuration: 0.25) { [weak self] in
//            self?.scrollView.alpha = (state == .collapsed) ? 0 : 1;
//        }
    }
}


