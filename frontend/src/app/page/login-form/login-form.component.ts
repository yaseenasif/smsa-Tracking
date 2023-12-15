import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { AuthService } from 'src/app/auth-service/auth.service';
import { jwtDecode } from 'jwt-decode';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss'],
  providers:[MessageService]
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

  
  constructor(private formBuilder:FormBuilder,private authService:AuthService,private router: Router,private messageService:MessageService) { }

  ngOnInit(): void {
    localStorage.clear();
    // this.loginForm = this.formBuilder.group({
    //   name:['',[Validators.required,Validators.min(7)]],
    //   password:['',[Validators.required,Validators.min(7)]]
    // })

    // this.changeBackgroundImage();
    // setInterval(()=>this.changeBackgroundImage(),5000)

  }

  // changeBackgroundImage(){
  //   this.currentImageUrl = `/assets/images/${this.images[this.currentIndex]}`
  //   this.currentIndex = (this.currentIndex + 1) % this.images.length;
  // }
  
  email!:string
  password!:string
  error:boolean=false;
  

  login(credentials:any){
    
    this.authService.login(credentials).subscribe((res:any)=>{
      
      localStorage.setItem("accessToken", res.accessToken);
      let token= jwtDecode(res.accessToken)
      console.log(token);
      
      this.router.navigate(['/home']);

    },(error:any)=>{
     
      this.error=true;
      this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
    })
    
  }

  showPassword(){

  }



}
