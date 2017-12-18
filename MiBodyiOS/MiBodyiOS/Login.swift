//
//  Login.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 11/12/16.
//  Copyright © 2016 MiBody. All rights reserved.
//

import UIKit
import FBSDKLoginKit
import Firebase

class Login: UIViewController {
    
    @IBOutlet weak var email: UITextField!
    @IBOutlet weak var password: UITextField!
    
    @IBOutlet weak var loginBtn: UIButton!
    @IBOutlet weak var facebookBtn: UIButton!
    @IBOutlet weak var signupBtn: UIButton!
    

    override func viewDidLoad() {
        super.viewDidLoad()

        //initialising firebase
        //FIRApp.configure()
        
        // Do any additional setup after loading the view.
        /*
        email.layer.cornerRadius = 20
        email.clipsToBounds = true
        email.borderStyle = .none
        email.layoutIfNeeded()
        let border = CALayer()
        let width = CGFloat(2.0)
        border.borderColor = UIColor.white.cgColor
        
        print(email.frame)
        border.frame = CGRect(x: 0, y: email.frame.size.height - width, width:  email.frame.size.width, height: email.frame.size.height)
        border.borderWidth = width
        
        email.layer.addSublayer(border)
        email.layer.masksToBounds = true
        
        password.layer.cornerRadius = 20
        
        password.clipsToBounds = true
        password.layer.addSublayer(border)
        password.layer.masksToBounds = true
        */
        loginBtn.layer.cornerRadius = 20
        loginBtn.clipsToBounds = true
        
        facebookBtn.layer.cornerRadius = 20
        facebookBtn.clipsToBounds = true
        signupBtn.layer.cornerRadius = 20
        signupBtn.clipsToBounds = true
        
        facebookBtn.addTarget(self, action: #selector(handleCustomFBLogin), for: .touchUpInside)
        loginBtn.addTarget(self, action: #selector(handleFirebaseLogin), for: .touchUpInside)

    }

    
    func handleCustomFBLogin() {
        FBSDKLoginManager().logIn(withReadPermissions: ["email"], from: self) { (result, err) in
            if err != nil {
                print("Custom FB Login failed:", err)
                return
            }
            
            self.showEmailAddress()
        }
    }
    
    func loginButtonDidLogOut(_ loginButton: FBSDKLoginButton!) {
        print("Did log out of facebook")
    }
    
    func loginButton(_ loginButton: FBSDKLoginButton!, didCompleteWith result: FBSDKLoginManagerLoginResult!, error: Error!) {
        if error != nil {
            print(error)
            return
        }
        
        showEmailAddress()
    }
    
    func showEmailAddress() {
        let accessToken = FBSDKAccessToken.current()
        guard let accessTokenString = accessToken?.tokenString else { return }
        
        let credentials = FIRFacebookAuthProvider.credential(withAccessToken: accessTokenString)
        FIRAuth.auth()?.signIn(with: credentials, completion: { (user, error) in
            if error != nil {
                print("Something went wrong with our FB user: ", error ?? "")
                return
            }
            
            print("Successfully logged in with our user: ", user ?? "")
        })
        
        FBSDKGraphRequest(graphPath: "/me", parameters: ["fields": "id, name, email, birthday"]).start { (connection, result, err) in
            
            if err != nil {
                print("Failed to start graph request:", err ?? "")
                return
            }
            print(result ?? "")
        }
    }
    
    
    func handleFirebaseLogin() {
        
        if (email.text?.isEmpty)! {
            let alert = UIAlertController(title: "Alert", message: "Email is missing", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "Edit", style: UIAlertActionStyle.default, handler: nil))
            self.present(alert, animated: true, completion: nil)
            return
            
        }
        if (password.text?.isEmpty)!{
            let alert = UIAlertController(title: "Alert", message: "Password is missing", preferredStyle: UIAlertControllerStyle.alert)
            alert.addAction(UIAlertAction(title: "Edit", style: UIAlertActionStyle.default, handler: nil))
            self.present(alert, animated: true, completion: nil)
            return
        }

        
        FIRAuth.auth()?.signIn(withEmail: email.text!, password: password.text!, completion: { (user, error) in
            
            if error != nil {
                
                var errMsg : String = "Some error occured, Try again"
                
                if let errCode = FIRAuthErrorCode(rawValue: error!._code) {
                    
                    switch errCode {
                    case .errorCodeInvalidEmail:
                        errMsg = "invalid email"
                    case .errorCodeUserNotFound:
                        errMsg = "User isn't existed"
                    case .errorCodeWrongPassword:
                        errMsg = "Wrong password"
                    default:
                        errMsg = "Some error occured, Try again"
                    }
                }
                
                let alert = UIAlertController(title: "Error", message: errMsg, preferredStyle: UIAlertControllerStyle.alert)
                alert.addAction(UIAlertAction(title: "OK", style: UIAlertActionStyle.default, handler: nil))
                self.present(alert, animated: true, completion: nil)
                return
            }
            else{
                print("Success")
            }
            
            //successfully logged in our user
            self.dismiss(animated: true, completion: nil)
            
        })
        
    }



}
