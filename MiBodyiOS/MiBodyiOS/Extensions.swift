//
//  Extensions.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 12/9/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import UIKit

extension UIColor {
    
    open class var primaryColor: UIColor {
        
        return mibodyOrange
    }
    
    open class var secondryColor: UIColor {
        
        return UIColor(red: 227/255, green: 31/255, blue: 49/255, alpha: 1)
    }
    
    open class var greyLightColor: UIColor {
        
        return UIColor(red: 245/255, green: 245/255, blue: 245/255, alpha: 1)
    }
    
    open class var greyMidColor: UIColor {
        
        return UIColor(red: 224/255, green: 224/255, blue: 224/255, alpha: 1)
    }
    
    open class var mibodyBlue: UIColor {
        
        return UIColor(red: 48/255, green: 73/255, blue: 153/255, alpha: 1)
    }
    
    open class var mibodyOrange: UIColor {
        
        return UIColor(red: 232/255, green: 76/255, blue: 61/255, alpha: 1)
    }
    
    open class var mibodyYellow: UIColor {
        
        return UIColor(red: 242/255, green: 231/255, blue: 48/255, alpha: 1)
    }
    
    open class var mibodyGreen: UIColor {
        
        return UIColor(red: 3/255, green: 194/255, blue: 0/255, alpha: 1)
    }
}

extension String {
    
    // Get Index of specific char in a String
    func indexOf(string: String) -> Int {
        
        var index = Int()
        let searchStartIndex = self.startIndex
        
        while searchStartIndex < self.endIndex,
            let range = self.range(of: string, range: searchStartIndex..<self.endIndex),
            !range.isEmpty
        {
            index = distance(from: self.startIndex, to: range.lowerBound)
            break
        }
        
        return index
    }
    
    // Convert HTML to Attributed String
    var html2AttributedString: NSAttributedString? {
        do {
            return try NSAttributedString(data: Data(utf8), options: [NSDocumentTypeDocumentAttribute: NSHTMLTextDocumentType, NSCharacterEncodingDocumentAttribute: String.Encoding.utf8.rawValue], documentAttributes: nil)
        } catch {
            print("error:", error)
            return nil
        }
    }
    
    var html2String: String {
        return html2AttributedString?.string ?? ""
    }
    
}

extension UIImage{
    
    class func textToImage(drawText text: String, imgFrame frame: CGRect) -> UIImage {
        
        let image = UIImage(named: "")
        
        let viewToRender = UIView(frame: CGRect(x: 0, y: 0, width: frame.width, height: frame.height))
        
        let imgView = UIImageView(frame: viewToRender.frame)
        
        imgView.image = image
        
        viewToRender.addSubview(imgView)
        
        let textImgView = UIImageView(frame: viewToRender.frame)
        
        textImgView.image = imageFrom(text: text, frame: frame)
        
        viewToRender.addSubview(textImgView)
        
        UIGraphicsBeginImageContextWithOptions(viewToRender.frame.size, false, 0)
        viewToRender.layer.render(in: UIGraphicsGetCurrentContext()!)
        let finalImage = UIGraphicsGetImageFromCurrentImageContext()
        UIGraphicsEndImageContext()
        
        return finalImage!
    }
    
    class func imageFrom(text: String , frame: CGRect) -> UIImage {
        
        let renderer = UIGraphicsImageRenderer(size: frame.size)
        let img = renderer.image { ctx in
            let paragraphStyle = NSMutableParagraphStyle()
            paragraphStyle.alignment = .center
            
            let attrs = [NSFontAttributeName: UIFont(name: "HelveticaNeue", size: 17)!, NSForegroundColorAttributeName: UIColor.black, NSParagraphStyleAttributeName: paragraphStyle]
            
            text.draw(with: CGRect(x: 0, y: 5, width: frame.width, height: frame.height), options: .usesLineFragmentOrigin, attributes: attrs, context: nil)
            
        }
        return img
    }
    
}

extension UIImageView {
    
    func tintColor(color newColor: UIColor){
        
        self.image = self.image?.withRenderingMode(.alwaysTemplate)
        self.tintColor = newColor
        
    }
}

extension UIView: UIGestureRecognizerDelegate {
    
    func addBorder(view: UIView, stroke: UIColor, fill: UIColor, radius: Int, width: CGFloat){
        // Add border
        let borderLayer = CAShapeLayer()
        
        let rectShape = CAShapeLayer()
        rectShape.bounds = view.frame
        rectShape.position = view.center
        
        rectShape.path = UIBezierPath(roundedRect: view.bounds, byRoundingCorners: [.bottomRight , .topLeft, .topRight, .bottomLeft], cornerRadii: CGSize(width: radius, height: radius)).cgPath
        borderLayer.path = rectShape.path // Reuse the Bezier path
        borderLayer.fillColor = fill.cgColor
        borderLayer.strokeColor = stroke.cgColor
        borderLayer.lineWidth = width
        borderLayer.frame = view.bounds
        view.layer.addSublayer(borderLayer)
        view.layer.mask = rectShape
        
    }
    
    func removeBorder(view: UIView){
        // remove border
        view.layer.removeFromSuperlayer()
    }
    
    func dropShadow() {
        
        self.layer.shadowColor = UIColor.black.cgColor
        self.layer.shadowOpacity = 0.4
        self.layer.shadowOffset = CGSize(width: -1, height: 0)
        self.layer.shadowRadius = 3
        
        self.layer.shadowPath = UIBezierPath(rect: self.bounds).cgPath
        self.layer.shouldRasterize = true
        self.layer.cornerRadius = 15
        self.layer.masksToBounds = true
    }
    
    func dropShadow2() {
        
        self.layer.shadowColor = UIColor.black.cgColor
        self.layer.shadowOpacity = 0.4
        self.layer.shadowOffset = CGSize(width: -1, height: 0)
        self.layer.shadowRadius = 9
        
        //self.layer.shadowPath = UIBezierPath(rect: self.bounds).cgPath
        self.layer.shouldRasterize = true
        //self.layer.cornerRadius = 15
        self.layer.masksToBounds = false
    }
    
    
    func outerGlow() {
        
        self.layer.shadowColor = UIColor(red: 252/255, green: 247/255, blue: 192/255, alpha: 1).cgColor
        self.layer.shadowOpacity = 2
        self.layer.shadowOffset = CGSize(width: -1, height: 0)
        self.layer.shadowRadius = 5
        
        //self.layer.shadowPath = UIBezierPath(rect: self.bounds).cgPath
        self.layer.shouldRasterize = true
        //self.layer.cornerRadius = 5
        
        self.layer.masksToBounds = false
    }
    
    func addBackBtn(selector: Selector) {
        
        let backBtnV = UIView(frame: CGRect(x: 10, y: 25, width: 80, height: 25))
        
        let backArrowIV = UIImageView(image: UIImage(named: "arrow"))
        backArrowIV.frame = CGRect(x: 0, y: 0, width: 15, height: backBtnV.frame.height)
        backArrowIV.contentMode = .scaleAspectFit
        backArrowIV.tintColor(color: UIColor.white) // extension
        
        let backBtn = UIButton(frame: CGRect(x: backArrowIV.frame.maxX, y: 0, width: backBtnV.frame.width - backArrowIV.frame.width - 15, height: backBtnV.frame.height))
        backBtn.setTitle("Back", for: .normal)
        backBtn.titleLabel?.textAlignment = .left
        backBtn.addTarget(self, action: selector, for: .touchUpInside)

        backBtnV.addSubview(backArrowIV)
        backBtnV.addSubview(backBtn)
        
        let backTap = UITapGestureRecognizer(target: self, action: selector)
        backTap.delegate = self
        backBtnV.addGestureRecognizer(backTap)
        
        self.addSubview(backBtnV)
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
