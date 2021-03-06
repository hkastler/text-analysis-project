import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  
  navbarOpen:boolean;

  constructor() {
    this.navbarOpen = false;
   }

  ngOnInit() {
  }

  toggleNavbar() {
    this.navbarOpen = !this.navbarOpen;
  }

}
