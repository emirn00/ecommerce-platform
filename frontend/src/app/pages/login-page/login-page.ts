import {Component, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import {AuthService} from '../../services/auth-service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  standalone: false,
  templateUrl: './login-page.html',
  styleUrl: './login-page.scss'
})
export class LoginPage implements OnInit {

  loginForm!: FormGroup;
  errorMessage: string = '';

  constructor(private fb: FormBuilder , private authService : AuthService , private router: Router) {
  }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      const loginData = this.loginForm.value;

      this.authService.login(loginData).subscribe({
        next: (response) => {
          console.log('Login successful:', response);
          localStorage.setItem('token', response.token);
          this.router.navigate(['/dashboard']); // örnek bir rota
        },
        error: (error) => {
          console.error('Login failed:', error);
          this.errorMessage = 'Giriş başarısız! Lütfen bilgilerinizi kontrol edin.';
        }
      });
    } else {
      this.errorMessage = 'Form geçerli değil.';
    }
  }


}
