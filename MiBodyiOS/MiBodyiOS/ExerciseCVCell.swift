//
//  ExerciseCVCell.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 12/10/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import UIKit

class ExerciseCVCell: UICollectionViewCell {
    
    let whiteV = UIView()
    let iconIV = UIImageView()
    let nameLbl = UILabel()
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        
        let cellWidth = frame.width //contentView.frame.width / 3
        
        whiteV.frame = CGRect(x: cellWidth * 0.2, y: 0, width: cellWidth * 0.6, height: cellWidth * 0.6)
        whiteV.layer.cornerRadius = 20
        whiteV.backgroundColor = UIColor.white
        
        iconIV.frame = CGRect(x: 5, y: 5, width: whiteV.frame.width - 10, height: whiteV.frame.height - 10)
        iconIV.contentMode = .scaleAspectFit
        iconIV.layer.masksToBounds = true
        iconIV.tintColor(color: UIColor.mibodyOrange)
        
        nameLbl.frame = CGRect(x: 0, y: iconIV.frame.maxY + 10, width: cellWidth, height: 21)
        nameLbl.font = UIFont.systemFont(ofSize: 15)
        nameLbl.textColor = UIColor.white
        nameLbl.textAlignment = .center
        
        whiteV.addSubview(iconIV)
        contentView.addSubview(whiteV)
        contentView.addSubview(nameLbl)
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
}
