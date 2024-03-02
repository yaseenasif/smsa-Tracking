import { Component } from '@angular/core';
import { MenuItem, MessageService } from 'primeng/api';
import { Routes } from '../../../model/ShipmentRoutes';
import { ActivatedRoute, Router } from '@angular/router';
import { DomesticRoutesService } from '../service/domestic-routes.service';
// import { LocationPort } from 'src/app/model/LocationPort';
import { LocationService } from '../../location/service/location.service';
import { DatePipe } from '@angular/common';
import { Location } from '../../../model/Location';
// import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-update-domestic-routes',
  templateUrl: './update-domestic-routes.component.html',
  styleUrls: ['./update-domestic-routes.component.scss'],
  providers: [MessageService, DatePipe]
})
export class UpdateDomesticRoutesComponent {

  items: MenuItem[] | undefined;
  routeId!: number;
  domesticRoutes: Routes = {
    id: null,
    destination: null,
    driver: null,
    eta: null,
    etd: null,
    origin: null,
    route: null,
    durationLimit: undefined,
    remarks: undefined
  }

  location!: Location[];
  selectedLocation!: Location;
  selectedOriginLocation!: Location;
  selectedDestinationLocation!: Location;


  routeNumbers: any;
  minETDDate: Date = new Date();
  // destination!: LocationPort[];

  constructor(
    private domesticRouteService: DomesticRoutesService,
    private domesticLocation: LocationService,
    private messageService: MessageService,
    private datePipe: DatePipe,
    private router: Router,
    private route: ActivatedRoute) { }


  ngOnInit(): void {
    this.routeId = +this.route.snapshot.paramMap.get('id')!;
    this.items = [{ label: 'Domestic Route List', routerLink: '/domestic-routes' }, { label: 'Edit Route' }];
    this.getDomesticLocations();
    this.getById(this.routeId);
  }

  getDomesticLocations() {
    this.domesticLocation.getAllLocationForDomestic().subscribe((res: Location[]) => {
      this.location = res.filter(el => el.status);
    }, error => {
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
  }

  getById(id: number) {
    this.domesticRouteService.getDomesticRouteById(id).subscribe((res: Routes) => {
      this.domesticRoutes = res;
      this.domesticRoutes.etd = this.domesticRoutes.etd ? new Date(`1970-01-01 ${this.domesticRoutes.etd}`) : null;
      this.domesticRoutes.eta = this.domesticRoutes.eta ? new Date(`1970-01-01 ${this.domesticRoutes.eta}`) : null;
    }, (error: any) => {
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
  }

  onSubmit() {
    
    this.domesticRoutes.etd = this.datePipe.transform(this.domesticRoutes.etd, 'HH:mm:ss')
    this.domesticRoutes.eta = this.datePipe.transform(this.domesticRoutes.eta, 'HH:mm:ss')
    this.editDomesticRoutes(this.domesticRoutes);
  }

  editDomesticRoutes(domesticRoutes: Routes) {
    
    this.domesticRouteService.updateDomesticRoute(this.routeId, domesticRoutes).subscribe((res: Routes) => {
      this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Domestic Route Updated Successfully' });
      setTimeout(() => {
        this.router.navigate(['/domestic-routes']);
      }, 800);
    }, (error: any) => {
      if (error.error.body) {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error.body });
      } else {
        this.messageService.add({ severity: 'error', summary: 'Error', detail: error.error });
      }
    })
  }

  onETDDateSelected(selectedETDDate: Date) {
    const minETDDate = new Date(selectedETDDate);

    minETDDate.setDate(minETDDate.getDate() + 1);

    this.minETDDate = minETDDate;
  }

}

