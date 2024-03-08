import { Component, OnInit } from '@angular/core';
import { MenuItem } from 'primeng/api';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

//   constructor() { }
//   items: MenuItem[] | undefined;
//   data: any;
//   options: any;
//   data1: any;
//   options1: any;

//   ngOnInit(): void {
//     this.items = [{ label: 'Dash Board'}];
//     const documentStyle = getComputedStyle(document.documentElement);
//     const textColor = documentStyle.getPropertyValue('--text-color');

//     this.data = {
//         labels: ['A', 'B', 'C'],
//         datasets: [
//             {
//                 data: [300, 50, 100],
//                 backgroundColor: ["#4099ff","#2ed8b6", "#FFB64D"],
//                 hoverBackgroundColor: ["#73b4ff","#59e0c5","#ffcb80"]
//             }
//         ]
//     };


//     this.options = {
//         cutout: '60%',
//         plugins: {
//             legend: {
//                 labels: {
//                     color: textColor,
//                     usePointStyle: true,
                
//                 },
//                 position:'bottom'
//             }
//         }
//     };

//     const documentStyle1= getComputedStyle(document.documentElement);
//     const textColor1 = documentStyle.getPropertyValue('--text-color');
//     const textColorSecondary1= documentStyle.getPropertyValue('--text-color-secondary');
//     const surfaceBorder1= documentStyle.getPropertyValue('--surface-border');
    
//     this.data1= {
//         labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
//         datasets: [
//             {
//                 label: 'My First dataset',
//                 backgroundColor:'#4099ff',
//                 borderColor:'#4099ff',
//                 data: [65, 59, 80, 81, 56, 55, 40]
//             },
//             {
//                 label: 'My Second dataset',
//                 backgroundColor:'#FF5370',
//                 borderColor:'#FF5370',
//                 data: [28, 48, 40, 19, 86, 27, 90]
//             }
//         ]
//     };

//     this.options1= {
//         maintainAspectRatio: false,
//         aspectRatio: 0.8,
//         plugins: {
//             legend: {
//                 labels: {
//                     color: textColor1,
//                     usePointStyle: true,
//                 },
//                 position:'bottom'
//             }
//         },
//         scales: {
//             x: {
//                 ticks: {
//                     color: textColorSecondary1,
//                     font: {
//                         weight: 500
//                     }
//                 },
//                 grid: {
//                     color: surfaceBorder1,
//                     drawBorder: false
//                 }
//             },
//             y: {
//                 ticks: {
//                     color: textColorSecondary1
//                 },
//                 grid: {
//                     color: surfaceBorder1,
//                     drawBorder: false
//                 }
//             }

//         }
//     };
//   }


items!: MenuItem[];

// products!: Product[];

chartData: any;

chartOptions: any;

// subscription!: Subscription;

// constructor(private productService: ProductService, public layoutService: LayoutService) {
//     this.subscription = this.layoutService.configUpdate$
//     .pipe(debounceTime(25))
//     .subscribe((config) => {
//         this.initChart();
//     });
// }

ngOnInit() {
    this.initChart();
    // this.productService.getProductsSmall().then(data => this.products = data);

    this.items = [
        { label: 'Add New', icon: 'pi pi-fw pi-plus' },
        { label: 'Remove', icon: 'pi pi-fw pi-minus' }
    ];
}

initChart() {
    const documentStyle = getComputedStyle(document.documentElement);
    const textColor = documentStyle.getPropertyValue('--text-color');
    const textColorSecondary = documentStyle.getPropertyValue('--text-color-secondary');
    const surfaceBorder = documentStyle.getPropertyValue('--surface-border');

    this.chartData = {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July'],
        datasets: [
            {
                label: 'First Dataset',
                data: [65, 59, 80, 81, 56, 55, 40],
                fill: false,
                backgroundColor: documentStyle.getPropertyValue('--bluegray-700'),
                borderColor: documentStyle.getPropertyValue('--bluegray-700'),
                tension: .4
            },
            {
                label: 'Second Dataset',
                data: [28, 48, 40, 19, 86, 27, 90],
                fill: false,
                backgroundColor: documentStyle.getPropertyValue('--green-600'),
                borderColor: documentStyle.getPropertyValue('--green-600'),
                tension: .4
            }
        ]
    };

    this.chartOptions = {
        plugins: {
            legend: {
                labels: {
                    color: textColor
                }
            }
        },
        scales: {
            x: {
                ticks: {
                    color: textColorSecondary
                },
                grid: {
                    color: surfaceBorder,
                    drawBorder: false
                }
            },
            y: {
                ticks: {
                    color: textColorSecondary
                },
                grid: {
                    color: surfaceBorder,
                    drawBorder: false
                }
            }
        }
    };
}

// ngOnDestroy() {
//     if (this.subscription) {
//         this.subscription.unsubscribe();
//     }
// }
}
