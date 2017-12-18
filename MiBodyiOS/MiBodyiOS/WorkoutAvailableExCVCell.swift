//
//  WorkoutAvailableExCVCell.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 12/11/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import UIKit

class WorkoutAvailableExCVCell: UICollectionViewCell {
    
    let bV = UIView()
    let iconIV = UIImageView()
    
    override init(frame: CGRect) {
        super.init(frame: frame)
        
        let cellWidth = frame.width
        
        bV.frame = CGRect(x: 20, y: 20, width: cellWidth - 40, height: cellWidth - 40)
        bV.layer.cornerRadius = 20
        bV.backgroundColor = UIColor.mibodyOrange
        
        iconIV.frame = CGRect(x: 5, y: 5, width: bV.frame.width - 10, height: bV.frame.height - 10)
        iconIV.contentMode = .scaleAspectFit
        iconIV.layer.masksToBounds = true
        iconIV.tintColor(color: UIColor.white)
        
        
        bV.addSubview(iconIV)
        contentView.addSubview(bV)
    }
    
    required init?(coder aDecoder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
}
