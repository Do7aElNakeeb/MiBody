//
//  WorkoutExConfigCVCell.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 12/11/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import UIKit
import FSPagerView

class WorkoutExConfigCVCell: FSPagerViewCell {
    
//    let bgV = UIView()
    var iconIV = UIImage()
    let nameLbl = UILabel()
    let countV = UIView()
    let exCountLbl = UILabel()
    let indexLbl = UILabel()
    let exBtn = UIButton()
    
    weak var workoutExConfigCellDelegate: WorkoutExConfigCellDelegate?
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        let cellWidth = frame.width //contentView.frame.width / 3
        
        indexLbl.frame = CGRect(x: 0, y: 0, width: cellWidth, height: 15)
        indexLbl.textColor = UIColor.black
        indexLbl.font = UIFont.boldSystemFont(ofSize: 14)
        indexLbl.textAlignment = .center
        
        countV.frame = CGRect(x: cellWidth * 0.65, y: indexLbl.frame.maxY, width: cellWidth * 0.15, height: cellWidth * 0.15)
        countV.backgroundColor = UIColor.darkGray
        countV.layer.cornerRadius = cellWidth * 0.15 / 2
        
        exCountLbl.frame = CGRect(x: 0, y: 0, width: countV.frame.width, height: countV.frame.height)
        exCountLbl.textColor = UIColor.white
        exCountLbl.textAlignment = .center
        exCountLbl.font = UIFont.systemFont(ofSize: 12)
        
        countV.addSubview(exCountLbl)
        
        exBtn.frame = CGRect(x: cellWidth * 0.1, y: countV.frame.maxY - countV.frame.height / 2, width: cellWidth * 0.8, height: cellWidth * 0.8)
        exBtn.layer.cornerRadius = 30
        exBtn.backgroundColor = UIColor.mibodyOrange
        exBtn.setTitle("", for: .normal)
        exBtn.tintColor = UIColor.white
//        exBtn.imageView?.tintColor(color: UIColor.white)
        exBtn.imageView?.contentMode = .scaleAspectFit
//        exBtn.imageView?.layer.masksToBounds = true
        exBtn.imageEdgeInsets.bottom = 15
        exBtn.imageEdgeInsets.top = 15
        exBtn.imageEdgeInsets.right = 15
        exBtn.imageEdgeInsets.left = 15
        exBtn.addTarget(self, action: #selector(exBtnClicked), for: .touchUpInside)
        
        //        bgV.frame = CGRect(x: cellWidth * 0.2, y: indexLbl.frame.maxY, width: cellWidth * 0.6, height: cellWidth * 0.6)
        //        bgV.layer.cornerRadius = 20
        //        bgV.backgroundColor = UIColor.mibodyOrange
//        iconIV.frame = CGRect(x: 5, y: 5, width: bgV.frame.width - 10, height: bgV.frame.height - 10)
//        iconIV.contentMode = .scaleAspectFit
//        iconIV.layer.masksToBounds = true
//        iconIV.tintColor(color: UIColor.white)
//
//        bgV.addSubview(iconIV)
        
        nameLbl.frame = CGRect(x: 0, y: exBtn.frame.maxY + 10, width: cellWidth, height: 21)
        nameLbl.font = UIFont.systemFont(ofSize: 15)
        nameLbl.textColor = UIColor.black
        nameLbl.textAlignment = .center
        
        contentView.addSubview(exBtn)
        contentView.addSubview(nameLbl)
        contentView.addSubview(countV)
        contentView.addSubview(indexLbl)
        
        contentView.backgroundColor = UIColor.clear
        
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func exBtnClicked(){
        
        workoutExConfigCellDelegate?.selectItemAt(index: Int(indexLbl.text!)! - 1)
    }
}

protocol WorkoutExConfigCellDelegate: class {
    
    func selectItemAt(index: Int)
}
