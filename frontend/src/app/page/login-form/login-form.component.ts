import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss']
})
export class LoginFormComponent implements OnInit {

  
  loginForm!:FormGroup;
  images:String[]=[
    'healthcare_upd_eng4.png',
    'smsa_ksamap_eng2.png',
    'FINAL_smartshipenglish2.png'
  ]
  currentIndex:any = 0;
  currentImageUrl!:string;
  togglePassword:any;

  constructor(private formBuilder:FormBuilder) { }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      email:['',[Validators.required,Validators.email]],
      password:['',[Validators.required,Validators.min(7)]]
    })

    this.changeBackgroundImage();
    setInterval(()=>this.changeBackgroundImage(),5000)

  }

  changeBackgroundImage(){
    this.currentImageUrl = `/assets/images/${this.images[this.currentIndex]}`
    this.currentIndex = (this.currentIndex + 1) % this.images.length;
  }

  login(){
    
  }

  showPassword(){

  }



}
