import { MenuItem, MessageService } from 'primeng/api';
import { Component } from '@angular/core';
import { Routes } from '../../../model/ShipmentRoutes';
import { DomesticRoutesService } from '../service/domestic-routes.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-domestic-routes',
  templateUrl: './add-domestic-routes.component.html',
  styleUrls: ['./add-domestic-routes.component.scss'],
  providers: [MessageService]
})
export class AddDomesticRoutesComponent {

  items: MenuItem[] | undefined;
  routes: Routes = {
    id: null,
    destination: null,
    driver: null,
    eta: null,
    etd: null,
    origin: null,
    route: null,
  }

  drivers: any[] = ["Domestic", "International"];

  routeNumbers: any;


  constructor(private domesticRouteService: DomesticRoutesService,
    private messageService: MessageService,
    private router: Router) { }


  ngOnInit(): void {
    this.items = [{ label: 'Domestic Route List', routerLink: '/domestic-route' }, { label: 'Add Route' }];
  }

  onSubmit() {
    this.domesticRouteService.addDomesticRoute(this.routes).subscribe(res => {
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Location is added' });
      setTimeout(() => {
        this.router.navigate(['/domestic-route']);
      }, 800);
    }, error => {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Route is not added' });
    })
  }

  getDomesticRoute() {
    this.routeNumbers = []
    debugger
    if (this.routes.origin !== null && this.routes.destination !== null) {
      this.domesticRouteService.getDomesticRoute(this.routes.origin!, this.routes.destination!).subscribe((res: any) => {
        this.routes = res;
        debugger
      }, (error: any) => {
        console.log(error);
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      })

    } else {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'You must have to select origin and destination port' });
    }
  }
}

