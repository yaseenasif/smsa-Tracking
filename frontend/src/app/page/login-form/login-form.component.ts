import { AfterViewInit, Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { AuthService } from 'src/app/auth-service/auth.service';
import { jwtDecode } from 'jwt-decode';
import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.scss'],
  providers: [MessageService],
})
export class LoginFormComponent implements AfterViewInit {
  loginForm!: FormGroup;
  images: String[] = [
    'healthcare_upd_eng4.png',
    'smsa_ksamap_eng2.png',
    'FINAL_smartshipenglish2.png',
  ];
  currentIndex: any = 0;
  currentImageUrl!: string;
  togglePassword: any;

  constructor(
    private authService: AuthService,
    private router: Router,
    private messageService: MessageService
  ) {}
  ngAfterViewInit(): void {
    const token = localStorage.getItem('accessToken');
    debugger;
    if (token != null) {
      if (this.isTokenExpired(token)) {
        this.messageService.add({
          severity: 'info',
          summary: 'Info',
          detail:
            'For enhanced security, your access token has automatically expired after a period of inactivity. Please refresh your session by logging in.',
        });
      }
    }
    localStorage.removeItem('accessToken');
  }

  email!: string;
  password!: string;
  error: boolean = false;
  private jwtHelper: JwtHelperService = new JwtHelperService();

  login(credentials: any) {
    this.authService.login(credentials).subscribe(
      (res: any) => {
        localStorage.setItem('accessToken', res.accessToken);

        this.router.navigate(['/home']);
      },
      (error: any) => {
        console.log(error);

        this.error = true;
        // this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      }
    );
  }

  isTokenExpired(token: string): boolean {
    return this.jwtHelper.isTokenExpired(token);
  }
}
