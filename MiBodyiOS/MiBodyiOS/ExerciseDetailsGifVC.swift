//
//  ExerciseDetailsGifVC.swift
//  MiBodyiOS
//
//  Created by Mamdouh El Nakeeb on 12/10/17.
//  Copyright © 2017 MiBody. All rights reserved.
//

import UIKit
import FLAnimatedImage

class ExerciseDetailsGifVC: UIViewController {

    @IBOutlet weak var gifIV: FLAnimatedImageView!
    
    var gif = ""
    override func viewDidLoad() {
        super.viewDidLoad()

        // Do any additional setup after loading the view.
        
        gifIV.contentMode = .scaleAspectFit
        
        if let path =  Bundle.main.path(forResource: gif, ofType: "gif") {
            if let data = NSData(contentsOfFile: path) {
                let gif = FLAnimatedImage(animatedGIFData: data as Data!)
                gifIV.animatedImage = gif
            }
        }
        
    }

}
