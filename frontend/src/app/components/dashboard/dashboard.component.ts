import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';
import { DashboardService } from './dashboard.service';
import { CardsData } from 'src/app/model/CardData';
import { UserService } from 'src/app/page/user/service/user.service';
import { User } from 'src/app/model/User';
import { forkJoin } from 'rxjs';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

    chartData: any;
    chartOptions: any;
    basicData: any;
    basicOptions: any;
    DomesticCardsData:CardsData={
        TotalShipments: 0,
        Outbounds: 0,
        Inbounds: 0
    };
    IntAirCardsData:CardsData={
        TotalShipments: 0,
        Outbounds: 0,
        Inbounds: 0
    };
    IntRoadCardsData:CardsData={
        TotalShipments: 0,
        Outbounds: 0,
        Inbounds: 0
    };
    date!:Date;
    currentYear:Date=new Date();
    typesWithOutDuplicate!:(string | null | undefined)[]


    constructor(private dashboardService:DashboardService,
                private userService:UserService){}

    ngOnInit(): void {
      const documentStyle = getComputedStyle(document.documentElement);
        const textColor = documentStyle.getPropertyValue('--text-color');

        this.chartData = {
            labels: ['A', 'B', 'C'],
            datasets: [
                {
                    data: [540, 325, 702],
                    backgroundColor: [documentStyle.getPropertyValue('--blue-500'), documentStyle.getPropertyValue('--yellow-500'), documentStyle.getPropertyValue('--green-500')],
                    hoverBackgroundColor: [documentStyle.getPropertyValue('--blue-400'), documentStyle.getPropertyValue('--yellow-400'), documentStyle.getPropertyValue('--green-400')]
                }
            ]
        };

        this.chartOptions = {
            plugins: {
                legend: {
                    labels: {
                        usePointStyle: true,
                        color: textColor
                    }
                }
            }
        };

        const documentStyleBar = getComputedStyle(document.documentElement);
        const textColorBar = documentStyleBar.getPropertyValue('--text-color');
        const textColorSecondaryBar = documentStyle.getPropertyValue('--text-color-secondary');
        const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

        this.basicData = {
            labels: ['Q1', 'Q2', 'Q3', 'Q4'],
            datasets: [
                {
                    label: 'Sales',
                    data: [540, 325, 702, 620],
                    backgroundColor: ['rgba(255, 159, 64, 0.2)', 'rgba(75, 192, 192, 0.2)', 'rgba(54, 162, 235, 0.2)', 'rgba(153, 102, 255, 0.2)'],
                    borderColor: ['rgb(255, 159, 64)', 'rgb(75, 192, 192)', 'rgb(54, 162, 235)', 'rgb(153, 102, 255)'],
                    borderWidth: 1
                }
            ]
        };

        this.basicOptions = {
            plugins: {
                legend: {
                    labels: {
                        color: textColorBar
                    }
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        color: textColorSecondaryBar
                    },
                    grid: {
                        color: surfaceBorder,
                        drawBorder: false
                    }
                },
                x: {
                    ticks: {
                        color: textColorSecondaryBar
                    },
                    grid: {
                        color: surfaceBorder,
                        drawBorder: false
                    }
                }
            }
        };
     
     
        this.userService.getLoggedInUser().subscribe((res:User)=>{        
                let types=res.locations?.map((el)=>{return el.type})
               this.typesWithOutDuplicate= types!.filter((item, index) => types!.indexOf(item) === index); 
                 this.getCardsData(this.currentYear)
                  
        },(_error)=>{})
    }

    getCardsData(year:Date){
        // this.dashboardService.DomesticCardsData(year.getFullYear()).subscribe((res)=>{
        //    this.cardsData=res;
        // },(error)=>{})
        forkJoin([this.dashboardService.DomesticCardsData(year.getFullYear()),this.dashboardService.InternationalAirCardsData(year.getFullYear()),this.dashboardService.InternationalRoadCardsData(year.getFullYear())]).subscribe({next:([DomRes,IntAirRes,IntRoadRes])=>{
        this.DomesticCardsData=DomRes;
        this.IntAirCardsData=IntAirRes;
        this.IntRoadCardsData=IntRoadRes;
        },error:(error)=>{}})
    }

    ifDomesticExist():boolean{
        return this.typesWithOutDuplicate.includes("Domestic")
    }
    ifInternationalAirExist():boolean{
        return this.typesWithOutDuplicate.includes("International Air")
    }
    ifInternationalRoadExist():boolean{
        return this.typesWithOutDuplicate.includes("International Road")
    }


}
