//
//  Exercise.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 12/9/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import Foundation

class Exercise{
    
    var name = ""
    var icon = ""
    var img = ""
    var gif = ""
    var description = ""
 
    
    init() {
        
    }
    
    init(name: String, icon: String, img: String, gif: String, description: String) {
    
        self.name = name
        self.icon = icon
        self.img = img
        self.gif = gif
        self.description = description
        
    }

}
