package de.thws

import de.thws.database.{MigrationComponent, TransactionServiceComponent}
import de.thws.repository.WaffleTransactionsRepositoryComponent
import de.thws.route.{MarketplaceRouteComponent, TradingRouteComponent, WaffleTradingRouteComponent}
import de.thws.service.{WafflePriceServiceComponent, WafflePriceUpdateServiceComponent, WaffleTransactionServiceComponent}
import de.thws.database.JdbcConnectionsComponent
import de.thws.json.TradeRequestJsonFormatComponent

trait Components
  extends
    DatabaseConfigurationComponent,
    JdbcConnectionsComponent,
    MigrationComponent,
    TransactionServiceComponent,
    WaffleTransactionServiceComponent,
    WaffleTradingRouteComponent,
    WaffleTransactionsRepositoryComponent,
    MarketplaceRouteComponent,
    TradingRouteComponent,
    WafflePriceServiceComponent,
    WafflePriceUpdateServiceComponent,
    TradeRequestJsonFormatComponent
